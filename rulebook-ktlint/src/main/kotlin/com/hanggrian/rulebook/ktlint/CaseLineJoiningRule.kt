package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceWithMultipleNewlines
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-joining)
 */
public class CaseLineJoiningRule : Rule("case-line-joining") {
    override val tokens: TokenSet = TokenSet.create(WHEN_ENTRY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val whitespace =
            node
                .treePrev
                ?.takeIf { it.isWhitespaceWithMultipleNewlines() }
                ?: return
        emit(whitespace.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "case.line.joining"
    }
}
