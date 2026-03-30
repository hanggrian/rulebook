package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BOOLEAN_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FALSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.NULL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TRUE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-assertion) */
public class ComplicatedAssertionRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(CALL_EXPRESSION)

    override fun isTest(): Boolean = true

    override fun visit(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?: return
        val valueArgumentList = node.findChildByType(VALUE_ARGUMENT_LIST) ?: return
        val callReplacement =
            when (identifier.text) {
                "assertTrue", "assertFalse" -> {
                    val operationReference =
                        valueArgumentList
                            .findChildByType(VALUE_ARGUMENT)
                            ?.findChildByType(BINARY_EXPRESSION)
                            ?.findChildByType(OPERATION_REFERENCE)
                            ?: return
                    when (operationReference.firstChildNode.elementType) {
                        EQEQ, EQEQEQ -> "assertEquals"
                        EXCLEQ, EXCLEQEQEQ -> "assertNotEquals"
                        else -> return
                    }
                }

                "assertEquals", "assertNotEquals" -> {
                    val valueArguments =
                        valueArgumentList
                            .children20
                            .filter { it.elementType === VALUE_ARGUMENT }
                    val booleanConstants =
                        valueArguments
                            .mapNotNull { it.findChildByType(BOOLEAN_CONSTANT) }
                    when {
                        booleanConstants.any { TRUE_KEYWORD in it } -> "assertTrue"
                        booleanConstants.any { FALSE_KEYWORD in it } -> "assertFalse"
                        valueArguments.any { NULL in it } -> "assertNull"
                        else -> return
                    }
                }

                else -> return
            }
        emit(node.startOffset, Messages[MSG, callReplacement], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:complicated-assertion")
        private const val MSG = "complicated.assertion"
    }
}
