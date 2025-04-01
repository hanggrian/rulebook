package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMMA
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-switch) */
public class UnnecessarySwitchRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target single entry
        val whenEntry =
            node
                .children()
                .singleOrNull { it.elementType == WHEN_ENTRY }
                ?: return

        // checks for violation
        whenEntry
            .takeUnless { COMMA in it }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:unnecessary-switch")

        const val MSG = "unnecessary.switch"
    }
}
