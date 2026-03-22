package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.GT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTEGER_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTEGER_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-size-equality) */
public class ComplicatedSizeEqualityRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(BINARY_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val operationReference = node.findChildByType(OPERATION_REFERENCE) ?: return
        val operation =
            operationReference.findChildByType(GT)
                ?: operationReference.findChildByType(LT)
                ?: operationReference.findChildByType(EQEQ)
                ?: return
        node
            .findChildByType(INTEGER_CONSTANT)
            ?.findChildByType(INTEGER_LITERAL)
            ?.takeIf { it.text == "0" }
            ?: return
        val call =
            node
                .findChildByType(DOT_QUALIFIED_EXPRESSION)
                ?.children20
                ?.lastOrNull { it.elementType === REFERENCE_EXPRESSION }
                ?.findChildByType(IDENTIFIER)
                ?.takeIf { it.text == "size" }
                ?: return
        emit(
            call.startOffset,
            Messages[
                MSG,
                when (operation.elementType) {
                    GT, LT -> "isNotEmpty"
                    else -> "isEmpty"
                },
            ],
            false,
        )
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:complicated-size-equality")
        private const val MSG = "complicated.size.equality"
    }
}
