package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INLINE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#contract-function-definition) */
public class ContractFunctionDefinitionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find initial contract call
        node
            .findChildByType(BLOCK)
            ?.findChildByType(CALL_EXPRESSION)
            ?.takeIf { n ->
                n
                    .findChildByType(REFERENCE_EXPRESSION)
                    ?.findChildByType(IDENTIFIER)
                    ?.let { it.text == "contract" || it.text == "kotlin.contract" }
                    ?: return
            } ?: return

        // checks for violation
        node
            .takeUnless { it.hasModifier(INLINE_KEYWORD) }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:contract-function-definition")

        const val MSG = "contract.function.definition"
    }
}
