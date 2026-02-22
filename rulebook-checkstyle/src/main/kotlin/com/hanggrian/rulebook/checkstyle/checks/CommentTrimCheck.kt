package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.isEolCommentEmpty
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#comment-trim) */
public class CommentTrimCheck : RulebookAstCheck() {
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
        node
            .takeIf { it.isEolCommentEmpty() }
            ?.let { log(it, Messages[MSG]) }
        current
            .takeIf { it.isEolCommentEmpty() }
            ?.let { log(it, Messages[MSG]) }
    }

    private companion object {
        const val MSG = "comment.trim"
    }
}
