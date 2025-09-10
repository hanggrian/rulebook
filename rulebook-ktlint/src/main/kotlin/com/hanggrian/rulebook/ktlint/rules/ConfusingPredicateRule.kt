package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUNCTION_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IS_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.NOT_IS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PREFIX_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#confusing-predicate) */
public class ConfusingPredicateRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CALL_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip non-predicate call
        val predicateBlock =
            node
                .findChildByType(LAMBDA_ARGUMENT)
                ?.findChildByType(LAMBDA_EXPRESSION)
                ?.findChildByType(FUNCTION_LITERAL)
                ?.findChildByType(BLOCK)
                ?: return

        // find inverted predicate function
        val functionReplacement =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.text
                ?.let { PREDICATE_CALLS[it] }
                ?: return

        // checks for violation
        val (expression, msg) =
            predicateBlock
                .findChildByType(BINARY_EXPRESSION)
                ?.to(MSG_EQUALS)
                ?: predicateBlock
                    .findChildByType(PREFIX_EXPRESSION)
                    ?.to(MSG_NEGATES)
                ?: predicateBlock
                    .findChildByType(IS_EXPRESSION)
                    ?.to(MSG_NEGATES)
                ?: return
        expression
            .takeUnless { it.isChained() }
            ?.findChildByType(OPERATION_REFERENCE)
            ?.firstChildNode
            ?.elementType
            ?.takeIf { it == EXCLEQ || it == EXCL || it == NOT_IS }
            ?: return
        emit(node.startOffset, Messages.get(msg, functionReplacement), false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:confusing-predicate")

        private const val MSG_EQUALS = "confusing.predicate.equals"
        private const val MSG_NEGATES = "confusing.predicate.negates"

        private val PREDICATE_CALLS = mutableMapOf<String, String>()

        init {
            putPredicateCall("filter", "filterNot")
            putPredicateCall("filterTo", "filterNotTo")
            putPredicateCall("memoryOptimizedFilter", "memoryOptimizedFilterNot")
            putPredicateCall("takeIf", "takeUnless")
        }

        private fun putPredicateCall(positiveCall: String, negativeCall: String) {
            PREDICATE_CALLS[positiveCall] = negativeCall
            PREDICATE_CALLS[negativeCall] = positiveCall
        }

        private fun ASTNode.isChained() = BINARY_EXPRESSION in this || PREFIX_EXPRESSION in this
    }
}
