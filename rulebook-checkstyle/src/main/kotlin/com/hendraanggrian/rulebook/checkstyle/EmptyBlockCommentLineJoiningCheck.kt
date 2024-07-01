package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.next
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.NEWLINE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-block-comment-line-joining)
 */
public class EmptyBlockCommentLineJoiningCheck : JavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        // find matching sibling
        val next =
            node.next
                ?.takeIf { it.type == NEWLINE }
                ?.next
                ?.takeIf { it.type == LEADING_ASTERISK }
                ?: return

        // checks for violation
        next.next?.takeIf { it.type == NEWLINE } ?: return
        log(next.lineNumber, next.columnNumber, Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.block.comment.line.joining"
    }
}
