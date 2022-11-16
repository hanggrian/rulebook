package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.actualText
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See Guide](https://github.com/hendraanggrian/lints/blob/main/guides/docs/summary-continuation-first-word.md).
 */
class SummaryContinuationFirstWordCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "First word of paragraph continuation cannot be a '%s'."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        val siblings = node.parent.children
        val index = siblings.indexOf(node)

        // skips first line of paragraph
        if (siblings.getOrNull(index - 1) == null || siblings.getOrNull(index - 2) == null) {
            return
        }

        // while loop until line ends or has tags
        val sb = StringBuilder()
        for (i in index + 1 until siblings.size) {
            val next = siblings[i]
            when (next.type) {
                LEADING_ASTERISK -> break
                JAVADOC_TAG -> return
            }
            sb.append(next.actualText)
        }

        // check the first word of paragraph continuation
        val line = sb.trimStart()
        if (line.startsWith("{@code")) {
            log(node.lineNumber, ERROR_MESSAGE.format("code"))
        } else if (line.startsWith("{@link")) {
            log(node.lineNumber, ERROR_MESSAGE.format("link"))
        }
    }
}
