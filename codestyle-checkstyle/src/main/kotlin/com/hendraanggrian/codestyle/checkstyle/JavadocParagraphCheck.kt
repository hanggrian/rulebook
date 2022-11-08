package com.hendraanggrian.codestyle.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.EOF
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * **Guide**: [Documentation Paragraph](https://github.com/hendraanggrian/codestyle/blob/main/guides/documentation-paragraph.md).
 */
class JavadocParagraphCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "First word of a line cannot be a %s."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    override fun visitJavadocToken(node: DetailNode) {
        val start = node.parent.children.indexOf(node) + 1
        // skip tags
        for (i in start until node.parent.children.size) {
            when (node.parent.children[i].type) {
                LEADING_ASTERISK -> break
                JAVADOC_TAG -> return
            }
        }
        // check the first word of paragraph continuation
        val line = node.parent.children.getLineAfterAsterisk(start).trimStart()
        if (line.startsWith("{@code")) {
            log(node.lineNumber, ERROR_MESSAGE.format("code"))
        } else if (line.startsWith("{@link")) {
            log(node.lineNumber, ERROR_MESSAGE.format("link"))
        }
    }

    /** [DetailNode] can't get its sibling, so while loop from its parent. */
    private fun Array<DetailNode>.getLineAfterAsterisk(start: Int): String {
        val sb = StringBuilder()
        for (i in start until size) {
            val node = get(i)
            if (node.type == LEADING_ASTERISK /* reached new line */ ||
                node.type == EOF /* reached end of docs */
            ) {
                break
            }
            sb.append(node.actualText)
        }
        return sb.toString()
    }
}
