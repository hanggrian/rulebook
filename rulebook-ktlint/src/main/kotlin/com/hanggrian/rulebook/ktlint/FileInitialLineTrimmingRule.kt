package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#file-initial-line-trimming)
 */
public class FileInitialLineTrimmingRule : Rule("file-initial-line-trimming") {
    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node.firstChildNode
            ?.takeIf { it.isWhiteSpaceWithNewline() }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "file.initial.line.trimming"
    }
}
