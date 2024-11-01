package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isEolCommentEmpty
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-trimming) */
public class CommentLineTrimmingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // continue if this comment is first line
        node
            .previousSibling
            .takeUnless {
                it?.lineNo == node.lineNo - 1 &&
                    it.type == SINGLE_LINE_COMMENT
            } ?: return

        // iterate to find last
        var current = node
        while (current.nextSibling?.lineNo == current.lineNo + 1 &&
            current.nextSibling?.type == SINGLE_LINE_COMMENT
        ) {
            current = current.nextSibling
        }

        // skip blank comment
        current
            .takeUnless { it === node }
            ?: return

        // checks for violation
        if (node.isEolCommentEmpty()) {
            log(node, Messages[MSG])
        }
        if (current.isEolCommentEmpty()) {
            log(current, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "comment.line.trimming"
    }
}
