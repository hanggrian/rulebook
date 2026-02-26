package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.isComment
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONTINUE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DO_WHILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-continue) */
public class UnnecessaryContinueRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(FOR, WHILE, DO_WHILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val body = node.findChildByType(BODY) ?: return
        val `continue` =
            body
                .findChildByType(BLOCK)
                ?.children20
                ?.lastOrNull {
                    it.elementType !== WHITE_SPACE &&
                        it.elementType !== RBRACE &&
                        !it.isComment()
                } ?: body.children20.singleOrNull()
                ?: return
        `continue`
            .takeIf { it.elementType === CONTINUE }
            ?: return
        emit(`continue`.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:unnecessary-continue")
        private const val MSG = "unnecessary.continue"
    }
}
