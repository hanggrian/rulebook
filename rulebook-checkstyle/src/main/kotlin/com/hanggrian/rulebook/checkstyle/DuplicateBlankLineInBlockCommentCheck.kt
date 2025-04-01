package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.next
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.NEWLINE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-block-comment) */
public class DuplicateBlankLineInBlockCommentCheck : RulebookJavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        // find matching sibling
        val nextLeadingAsterisk =
            node
                .next
                ?.takeIf { it.type == NEWLINE }
                ?.next
                ?.takeIf { it.type == LEADING_ASTERISK }
                ?: return

        // checks for violation
        nextLeadingAsterisk
            .takeIf { it.next?.type == NEWLINE }
            ?: return
        log(nextLeadingAsterisk.lineNumber, nextLeadingAsterisk.columnNumber, Messages[MSG])
    }

    internal companion object {
        const val MSG = "duplicate.blank.line.in.block.comment"
    }
}
