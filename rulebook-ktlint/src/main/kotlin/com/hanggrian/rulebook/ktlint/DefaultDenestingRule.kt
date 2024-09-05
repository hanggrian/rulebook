package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasReturnOrThrow
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-denesting)
 */
public class DefaultDenestingRule : Rule("default-denesting") {
    override val tokens: TokenSet = TokenSet.create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip no default
        val cases = node.children().filter { it.elementType == WHEN_ENTRY }
        val default = cases.lastOrNull()?.takeIf { ELSE_KEYWORD in it } ?: return

        // checks for violation
        cases
            .toList()
            .dropLast(1)
            .takeIf { cases2 -> cases2.all { it.hasReturnOrThrow() } }
            ?: return
        emit(default.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "default.denesting"
    }
}
