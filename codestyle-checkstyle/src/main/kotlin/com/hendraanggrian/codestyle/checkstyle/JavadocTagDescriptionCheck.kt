package com.hendraanggrian.codestyle.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * **Guide**: [Documentation Tag Description](https://github.com/hendraanggrian/codestyle/blob/main/guides/documentation-tag-description.md).
 */
class JavadocTagDescriptionCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "%s description is not a sentence."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    /**
     * Some description
     *
     * @param node some node.
     */
    override fun visitJavadocToken(node: DetailNode) {
        // only enforce certain tags
        val tagName = node.children.first().text
        if (tagName != "@param" && tagName != "@return") {
            return
        }
        // check the suffix
        val tagDescription = node.actualText.trimComment().trimEnd()
        if (!tagDescription.endsWith('.') && !tagDescription.endsWith('?') &&
            !tagDescription.endsWith('!')
        ) {
            log(node.lineNumber, ERROR_MESSAGE.format(tagName))
        }
    }

    private fun String.trimComment(): String {
        val index = indexOf("//")
        if (index == -1) {
            return this
        }
        return substring(0, index).trimEnd()
    }
}
