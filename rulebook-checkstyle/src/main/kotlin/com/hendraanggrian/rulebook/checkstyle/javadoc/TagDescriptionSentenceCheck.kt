package com.hendraanggrian.rulebook.checkstyle.javadoc

import com.hendraanggrian.rulebook.checkstyle.actualText
import com.hendraanggrian.rulebook.checkstyle.contains
import com.hendraanggrian.rulebook.checkstyle.get
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.PARAM_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.RETURN_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.THROWS_LITERAL
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#tag-description-sentence).
 */
class TagDescriptionSentenceCheck : AbstractJavadocCheck() {
    private companion object {
        const val ERROR_MESSAGE = "Description of tag '%s' is not a sentence."

        private val END_PUNCTUATIONS = setOf('.', '?', '!')
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // skip no description
        if (DESCRIPTION !in node) {
            return
        }

        // only enforce certain tags
        val anyLiteral = when {
            PARAM_LITERAL in node -> node[PARAM_LITERAL]
            RETURN_LITERAL in node -> node[RETURN_LITERAL]
            THROWS_LITERAL in node -> node[THROWS_LITERAL]
            else -> return
        }

        // check the suffix
        val endPunctuation = node.actualText.trimComment().trimEnd().lastOrNull() ?: return
        if (endPunctuation !in END_PUNCTUATIONS) {
            log(anyLiteral.lineNumber, ERROR_MESSAGE.format(anyLiteral.text))
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
