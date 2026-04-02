package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.properties.ConfigurableTags
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes.TAG_NAME
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes.TEXT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation) */
public class BlockTagPunctuationCheck :
    RulebookJavadocCheck(),
    ConfigurableTags {
    override val tagSet: HashSet<String> = hashSetOf("@param", "@return")

    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_BLOCK_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // only enforce certain tags
        val tagNode =
            node
                .children()
                .firstOrNull { child ->
                    child.children().any { it.type == TAG_NAME && "@${it.text}" in tagSet }
                } ?: return
        val tagLiteral =
            tagNode
                .children()
                .firstOrNull { it.type == TAG_NAME }
                ?: return

        // long descriptions have multiple lines, take only the last one
        val tagText =
            tagNode
                .children()
                .firstOrNull { it.type == DESCRIPTION }
                ?.children()
                ?.lastOrNull { it.type == TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation
        val text = tagText.text
        text
            .lastOrNull()
            ?.takeUnless { it in END_PUNCTUATIONS }
            ?: return
        log(
            tagText.lineNumber,
            tagText.columnNumber + text.length,
            Messages[MSG, "@${tagLiteral.text}"],
        )
    }

    private companion object {
        const val MSG = "block.tag.punctuation"

        val END_PUNCTUATIONS = setOf('.', '!', '?', ')')
    }
}
