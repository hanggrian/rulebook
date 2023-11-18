package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.find
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.TEXT
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-block-tag-with-period).
 */
public class EndBlockTagWithPeriodCheck : AbstractJavadocCheck() {
    private var tags = setOf("@param", "@return")

    public fun setTags(vararg tags: String) {
        this.tags = tags.toSet()
    }

    public override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    public override fun visitJavadocToken(node: DetailNode) {
        // only enforce certain tags
        node.takeUnless { n -> n.children.none { it.text in tags } } ?: return

        // long descriptions have multiple lines, take only the last one
        val text =
            node.find(DESCRIPTION)?.children
                ?.findLast { it.type == TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation after trimming optional comment
        text.text.trimComment().trimEnd().lastOrNull()
            ?.takeIf { it !in END_PUNCTUATIONS }
            ?: return
        log(text.lineNumber, Messages[MSG])
    }

    internal companion object {
        const val MSG = "end.block.tag.with.period"

        private val END_PUNCTUATIONS = setOf('.', ')')

        private fun String.trimComment(): String {
            val index = indexOf("//")
            if (index == -1) {
                return this
            }
            return substring(0, index).trimEnd()
        }
    }
}
