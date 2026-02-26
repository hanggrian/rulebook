package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.isComment
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-return) */
public class UnnecessaryReturnRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val `return` =
            node
                .findChildByType(BLOCK)
                ?.children20
                ?.lastOrNull {
                    it.elementType !== WHITE_SPACE &&
                        it.elementType !== RBRACE &&
                        !it.isComment()
                }?.takeIf {
                    it.elementType === RETURN &&
                        it.children20.singleOrNull()?.elementType === RETURN_KEYWORD
                } ?: return
        emit(`return`.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:unnecessary-return")
        private const val MSG = "unnecessary.return"
    }
}
