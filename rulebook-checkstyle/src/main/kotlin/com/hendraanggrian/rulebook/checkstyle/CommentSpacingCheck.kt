package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#comment-spacing)
 */
public class CommentSpacingCheck : RulebookCheck() {
    public override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    public override fun isCommentNodesRequired(): Boolean = true

    public override fun visitToken(node: DetailAST) {
        // checks for violation
        node.findFirstToken(COMMENT_CONTENT)
            .takeIf { !it.text.startsWith(' ') }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "comment.spacing"
    }
}
