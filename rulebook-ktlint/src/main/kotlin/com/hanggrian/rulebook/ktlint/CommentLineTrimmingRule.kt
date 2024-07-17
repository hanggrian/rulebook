package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleNewline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-trimming)
 */
public class CommentLineTrimmingRule : Rule("comment-line-trimming") {
    override val tokens: TokenSet = TokenSet.create(EOL_COMMENT)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // continue if this comment is first line
        val prev = node.treePrev
        if (prev?.isWhitespaceSingleNewline() == true &&
            prev.treePrev?.elementType == EOL_COMMENT
        ) {
            return
        }

        // iterate to find last
        var current = node
        while (current.treeNext?.isWhitespaceSingleNewline() == true &&
            current.treeNext?.treeNext?.elementType == EOL_COMMENT
        ) {
            current = current.treeNext.treeNext
        }

        // skip blank comment
        if (current === node) {
            return
        }

        // checks for violation
        if (node.isEolCommentEmpty()) {
            emit(node.startOffset, Messages[MSG], false)
        }
        if (current.isEolCommentEmpty()) {
            emit(current.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "comment.line.trimming"
    }
}
