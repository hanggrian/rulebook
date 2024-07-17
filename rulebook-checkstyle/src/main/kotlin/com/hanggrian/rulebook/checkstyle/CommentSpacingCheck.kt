package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#comment-spacing)
 */
public class CommentSpacingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip empty comment
        val commentContent =
            node
                .findFirstToken(COMMENT_CONTENT)
                ?.takeUnless { it.text == "\n" }
                ?: return

        // checks for violation
        commentContent
            .takeUnless { it.text.startsWith(' ') }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "comment.spacing"
    }
}
