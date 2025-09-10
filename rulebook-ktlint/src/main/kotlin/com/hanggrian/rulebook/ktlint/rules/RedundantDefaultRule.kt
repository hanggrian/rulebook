package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.hasJumpStatement
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-default) */
public class RedundantDefaultRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find default
        val whenEntries =
            node
                .children()
                .filter { it.elementType == WHEN_ENTRY }
        val `else` =
            whenEntries
                .lastOrNull()
                ?.takeIf { ELSE_KEYWORD in it }
                ?: return

        // checks for violation
        whenEntries
            .toList()
            .dropLast(1)
            .takeIf { cases2 -> cases2.all { it.hasJumpStatement() } }
            ?: return
        emit(`else`.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:redundant-default")

        private const val MSG = "redundant.default"
    }
}
