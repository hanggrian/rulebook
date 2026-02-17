package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUNCTION_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INLINE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#missing-inline-in-contract) */
public class MissingInlineInContractRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find initial contract call
        val callExpression =
            node
                .findChildByType(BLOCK)
                ?.findChildByType(CALL_EXPRESSION)
                ?.takeIf { n ->
                    n
                        .findChildByType(REFERENCE_EXPRESSION)
                        ?.findChildByType(IDENTIFIER)
                        ?.text
                        ?.let { it == "contract" || it == "kotlin.contract" }
                        ?: return
                } ?: return

        // skip function without `callsInPlace`
        callExpression
            .findChildByType(LAMBDA_ARGUMENT)
            ?.findChildByType(LAMBDA_EXPRESSION)
            ?.findChildByType(FUNCTION_LITERAL)
            ?.findChildByType(BLOCK)
            ?.findChildByType(CALL_EXPRESSION)
            ?.findChildByType(REFERENCE_EXPRESSION)
            ?.findChildByType(IDENTIFIER)
            ?.text
            ?.takeIf { it == "callsInPlace" }
            ?: return

        // checks for violation
        node
            .takeUnless { it.hasModifier(INLINE_KEYWORD) }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:missing-inline-in-contract")
        private const val MSG = "missing.inline.in.contract"
    }
}
