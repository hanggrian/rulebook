package com.hendraanggrian.rulebook.checkstyle.docs

import com.hendraanggrian.rulebook.checkstyle.Messages
import com.hendraanggrian.rulebook.checkstyle.actualText
import com.hendraanggrian.rulebook.checkstyle.contains
import com.hendraanggrian.rulebook.checkstyle.getOrNull
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.PARAM_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.RETURN_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.THROWS_LITERAL
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/PunctuateTag).
 */
class PunctuateTagCheck : AbstractJavadocCheck() {
    private companion object {
        const val MSG = "punctuate.tag"

        val END_PUNCTUATIONS = setOf('.', '?', '!')
    }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // skip no description
        if (DESCRIPTION !in node) {
            return
        }

        // only enforce certain tags
        val tagLiteral = when {
            PARAM_LITERAL in node -> node.getOrNull(PARAM_LITERAL) ?: return
            RETURN_LITERAL in node -> node.getOrNull(RETURN_LITERAL) ?: return
            THROWS_LITERAL in node -> node.getOrNull(THROWS_LITERAL) ?: return
            else -> return
        }

        // check the suffix
        val punctuation = node.actualText.trimComment().trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            log(tagLiteral.lineNumber, Messages[MSG])
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
