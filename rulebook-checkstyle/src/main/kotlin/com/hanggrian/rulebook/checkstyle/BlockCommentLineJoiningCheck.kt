package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.next
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.NEWLINE

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-line-joining)
 */
public class BlockCommentLineJoiningCheck : RulebookJavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        // find matching sibling
        val next =
            node
                .next
                ?.takeIf { it.type == NEWLINE }
                ?.next
                ?.takeIf { it.type == LEADING_ASTERISK }
                ?: return

        // checks for violation
        next
            .next
            ?.takeIf { it.type == NEWLINE }
            ?: return
        log(next.lineNumber, next.columnNumber, Messages[MSG])
    }

    internal companion object {
        const val MSG = "block.comment.line.joining"
    }
}
