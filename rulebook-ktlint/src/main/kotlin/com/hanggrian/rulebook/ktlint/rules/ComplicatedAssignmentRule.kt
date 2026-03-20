package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DIV
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MINUS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MUL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PERC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PLUS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-assignment) */
public class ComplicatedAssignmentRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(BINARY_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target root assignment
        node
            .parent
            ?.takeUnless { it.elementType === BINARY_EXPRESSION }
            ?: return

        // skip shorthand operator
        val eq =
            node
                .findChildByType(OPERATION_REFERENCE)
                ?.findChildByType(EQ)
                ?: return

        // checks for violation
        val identifier =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?: return
        val binaryExpression = node.deepestBinaryExpression
        val operation =
            binaryExpression
                .findChildByType(OPERATION_REFERENCE)
                ?.firstChildNode
                ?.takeIf {
                    it.elementType === PLUS ||
                        it.elementType === MINUS ||
                        it.elementType === MUL ||
                        it.elementType === DIV ||
                        it.elementType === PERC
                } ?: return
        binaryExpression
            .findChildByType(REFERENCE_EXPRESSION)
            ?.findChildByType(IDENTIFIER)
            ?.takeIf { it.text == identifier.text }
            ?: return
        emit(eq.startOffset, Messages[MSG, operation.text + "="], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:complicated-assignment")
        private const val MSG = "complicated.assignment"

        private val ASTNode.deepestBinaryExpression: ASTNode
            get() {
                var current: ASTNode = this
                while (BINARY_EXPRESSION in current) {
                    current = current.findChildByType(BINARY_EXPRESSION)!!
                }
                return current
            }
    }
}
