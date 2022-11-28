package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.actualText
import com.hendraanggrian.lints.checkstyle.first
import com.hendraanggrian.lints.checkstyle.siblingsUntil
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See guide](https://github.com/hendraanggrian/lints/blob/main/rules.md#summary-continuation).
 */
class SummaryContinuationCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "First word of paragraph continuation cannot be a '%s'."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        // skips first line of paragraph
        if (node.parent.first(LEADING_ASTERISK) == node) {
            return
        }

        // skips if tag is found
        val leadingAsteriskLine = node.siblingsUntil(LEADING_ASTERISK)
        if (JAVADOC_TAG in leadingAsteriskLine.map { it.type }) {
            return
        }

        // check the first word of paragraph continuation
        val line = leadingAsteriskLine.joinToString("") { it.actualText }.trimStart()
        if (line.startsWith("{@code")) {
            log(node.lineNumber, ERROR_MESSAGE.format("code"))
        } else if (line.startsWith("{@link")) {
            log(node.lineNumber, ERROR_MESSAGE.format("link"))
        }
    }
}
