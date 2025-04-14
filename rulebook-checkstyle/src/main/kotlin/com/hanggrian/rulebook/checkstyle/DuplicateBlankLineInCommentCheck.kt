package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isEolCommentEmpty
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment) */
public class DuplicateBlankLineInCommentCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip comment with content
        node
            .takeIf { it.isEolCommentEmpty() }
            ?: return

        // find matching sibling
        val next =
            node
                .nextSibling
                ?.takeIf { it.isEolCommentEmpty() }
                ?: return

        // checks for violation
        next
            .takeIf { it.isEolCommentEmpty() }
            ?: return
        log(next, Messages[MSG])
    }

    private companion object {
        const val MSG = "duplicate.blank.line.in.comment"
    }
}
