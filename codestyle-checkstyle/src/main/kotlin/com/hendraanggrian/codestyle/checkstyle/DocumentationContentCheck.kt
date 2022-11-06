package com.hendraanggrian.codestyle.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.EOF
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * **Guide**: [Documentation Content](https://github.com/hendraanggrian/codestyle/blob/main/guides/documentation-content.md).
 */
class DocumentationContentCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "First word of a line cannot be a %s."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(LEADING_ASTERISK)

    /**
     * Some description
     * @param node some node.
     */
    override fun visitJavadocToken(node: DetailNode) {
        val line = node.lineAfterAsterisk.trimStart()
        if (line.startsWith("{@code")) {
            log(node.lineNumber, ERROR_MESSAGE.format("code"))
        } else if (line.startsWith("{@link")) {
            log(node.lineNumber, ERROR_MESSAGE.format("link"))
        }
    }

    /** [DetailNode] can't get its sibling, so while loop from its parent. */
    private val DetailNode.lineAfterAsterisk: String
        get() {
            val lines = parent.children.toList()
            val startingPoint = lines.indexOf(this) + 1
            val sb = StringBuilder()
            for (i in startingPoint until lines.size) {
                val node = lines[i]
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
