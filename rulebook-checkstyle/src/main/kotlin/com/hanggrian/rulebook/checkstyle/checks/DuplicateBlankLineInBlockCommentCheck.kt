package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.nextSibling
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
                .nextSibling
                ?.takeIf { it.type == NEWLINE }
                ?.nextSibling
                ?.takeIf { it.type == LEADING_ASTERISK }
                ?: return

        // checks for violation
        nextLeadingAsterisk
            .nextSibling
            ?.type
            ?.takeIf { it == NEWLINE }
            ?: return
        log(nextLeadingAsterisk.lineNumber, nextLeadingAsterisk.columnNumber, Messages[MSG])
    }

    private companion object {
        const val MSG = "duplicate.blank.line.in.block.comment"
    }
}
