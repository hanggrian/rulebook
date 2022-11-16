package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.actualText
import com.hendraanggrian.lints.checkstyle.contains
import com.hendraanggrian.lints.checkstyle.get
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.PARAMETER_NAME
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See Guide](https://github.com/hendraanggrian/lints/blob/main/guides/docs/tag-description-sentence.md).
 */
class TagDescriptionSentenceCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "Tag '%s' description is not a sentence."
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // skips no description
        if (DESCRIPTION !in node) {
            return
        }

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
            log(node[PARAMETER_NAME].lineNumber, ERROR_MESSAGE.format(tagName))
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
