package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-comment-line-joining)
 */
public class EmptyCommentLineJoiningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip comment with content
        node.takeIf { it.isEolCommentEmpty() } ?: return

        // find matching sibling
        val next =
            node.nextSibling
                ?.takeIf { it.type == SINGLE_LINE_COMMENT && it.isEolCommentEmpty() }
                ?: return

        // checks for violation
        next.takeIf { it.type == SINGLE_LINE_COMMENT && it.isEolCommentEmpty() } ?: return
        log(next, Messages[MSG_EOL])
    }

    internal companion object {
        const val MSG_EOL = "empty.comment.line.joining"

        private fun DetailAST.isEolCommentEmpty(): Boolean =
            firstChild.type == COMMENT_CONTENT && firstChild.text == "\n"
    }
}
