package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.TEXT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation) */
public class BlockTagPunctuationCheck : RulebookJavadocCheck() {
    public var tags: String = "@param, @return"

    internal val tagList get() = tags.split(',').map { it.trim() }

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // only enforce certain tags
        val tagLiteral =
            node
                .children
                .first()
                .takeIf { it.text in tagList }
                ?: return

        // long descriptions have multiple lines, take only the last one
        val text =
            node
                .children
                ?.firstOrNull { it.type == DESCRIPTION }
                ?.children
                ?.lastOrNull { it.type == TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation
        text
            .text
            .lastOrNull()
            ?.takeUnless { it in END_PUNCTUATIONS }
            ?: return
        log(text.lineNumber, text.columnNumber, Messages.get(MSG, tagLiteral.text))
    }

    private companion object {
        const val MSG = "block.tag.punctuation"

        val END_PUNCTUATIONS = setOf('.', '!', '?', ')')
    }
}
