package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-braces) */
public class UnnecessaryBracesRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(ELSE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .findChildByType(BLOCK)
            ?.children20
            ?.singleOrNull {
                it.elementType != WHITE_SPACE &&
                    it.elementType != LBRACE &&
                    it.elementType != RBRACE
            }?.takeIf { it.elementType == IF }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:unnecessary-braces")
        private const val MSG = "unnecessary.braces"
    }
}
