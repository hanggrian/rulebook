package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#comment-spaces) */
public class CommentSpacesCheck : RulebookAstCheck() {
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
            .text
            .takeIf { s ->
                !s.startsWith(' ') &&
                    !s.startsWith("noinspection") &&
                    !s.startsWith("region") &&
                    !s.startsWith("endregion") &&
                    !s.startsWith("language=") &&
                    !s.trimEnd().all { it == '/' }
            } ?: return
        log(node, Messages[MSG])
    }

    private companion object {
        const val MSG = "comment.spaces"
    }
}
