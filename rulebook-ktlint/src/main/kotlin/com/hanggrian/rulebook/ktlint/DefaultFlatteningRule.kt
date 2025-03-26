package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasJumpStatement
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening) */
public class DefaultFlatteningRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip no default
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

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:default-flattening")

        const val MSG = "default.flattening"
    }
}
