package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ANDAND
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BOOLEAN_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FALSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OROR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PARENTHESIZED
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PREFIX_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SAFE_ACCESS_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TRUE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-boolean-equality) */
public class ComplicatedBooleanEqualityRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(BINARY_EXPRESSION, PREFIX_EXPRESSION)

    override fun visit(node: ASTNode, emit: Emit) {
        when (node.elementType) {
            PREFIX_EXPRESSION -> {
                // checks for violation
                var current: ASTNode = node.takeIf { it.isNegate() } ?: return
                while (true) {
                    current = current.findChildByType(PARENTHESIZED) ?: break
                }
                current
                    .takeIf { it.findChildByType(PREFIX_EXPRESSION)?.isNegate() == true }
                    ?: return
                emit(node.startOffset, Messages[MSG_NEGATE], false)
            }

            BINARY_EXPRESSION -> {
                // skip nullable property
                node
                    .takeUnless { SAFE_ACCESS_EXPRESSION in it }
                    ?: return

                // checks for violation
                val operationReference = node.findChildByType(OPERATION_REFERENCE) ?: return
                operationReference.findChildByType(EQEQ)
                    ?: operationReference.findChildByType(EXCLEQ)
                    ?: operationReference.findChildByType(OROR)
                    ?: operationReference.findChildByType(ANDAND)
                    ?: return
                val booleanConstant = node.findChildByType(BOOLEAN_CONSTANT) ?: return
                val keyword =
                    booleanConstant.findChildByType(TRUE_KEYWORD)
                        ?: booleanConstant.findChildByType(FALSE_KEYWORD)
                        ?: return
                emit(keyword.startOffset, Messages[MSG_CONSTANT], false)
            }
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:complicated-boolean-equality")
        private const val MSG_CONSTANT = "complicated.boolean.equality.constant"
        private const val MSG_NEGATE = "complicated.boolean.equality.negate"

        private fun ASTNode.isNegate(): Boolean {
            val operationReference = findChildByType(OPERATION_REFERENCE) ?: return false
            return EXCL in operationReference
        }
    }
}
