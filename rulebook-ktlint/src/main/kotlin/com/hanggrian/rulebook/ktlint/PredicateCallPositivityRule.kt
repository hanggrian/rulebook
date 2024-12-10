package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUNCTION_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PREFIX_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#predicate-call-positivity) */
public class PredicateCallPositivityRule : RulebookRule(ID) {
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
                ?.let { PREDICATE_CALLS[it.text] }
                ?: return

        // checks for violation
        val expression =
            predicateBlock
                .findChildByType(BINARY_EXPRESSION)
                ?.takeUnless { it.isNested() }
                ?.findChildByType(OPERATION_REFERENCE)
                ?.firstChildNode
                ?.takeIf { it.elementType == EXCLEQ }
        if (expression != null) {
            emit(node.startOffset, Messages.get(MSG_BINARY, functionReplacement), false)
            return
        }
        predicateBlock
            .findChildByType(PREFIX_EXPRESSION)
            ?.takeUnless { it.isNested() }
            ?.findChildByType(OPERATION_REFERENCE)
            ?.firstChildNode
            ?.takeIf { it.elementType == EXCL }
            ?: return
        emit(node.startOffset, Messages.get(MSG_PREFIX, functionReplacement), false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:predicate-call-positivity")

        const val MSG_BINARY = "predicate.call.positivity.binary"
        const val MSG_PREFIX = "predicate.call.positivity.prefix"

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

        private fun ASTNode.isNested() = BINARY_EXPRESSION in this || PREFIX_EXPRESSION in this
    }
}
