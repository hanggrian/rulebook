package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.find
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.NEWLINE
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.PARAM_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.RETURN_LITERAL
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.TEXT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-sentence-with-period).
 */
class EndTagSentenceWithPeriodCheck : AbstractJavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC_TAG)

    override fun visitJavadocToken(node: DetailNode) {
        // Skip no description.
        val description = node.find(DESCRIPTION) ?: return

        // Only enforce certain tags.
        if (node.children.none { it.type in TAG_TARGETS }) {
            return
        }

        // Long descriptions have multiple lines, take only the last one.
        val text =
            description.children.findLast { it.type == TEXT && it.text.isNotBlank() } ?: return

        // Checks for violation after trimming optional comment.
        val punctuation = text.text.trimComment().trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            log(text.lineNumber, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "end.sentence.with.period.tag"

        private val TAG_TARGETS = setOf(PARAM_LITERAL, RETURN_LITERAL)
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

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-sentence-with-period).
 */
class EndCommentSentenceWithPeriodCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SINGLE_LINE_COMMENT)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // Long descriptions have multiple lines, take only the last one.
        if (node.nextSibling?.type == NEWLINE &&
            node.nextSibling?.nextSibling?.type == SINGLE_LINE_COMMENT
        ) {
            return
        }

        // Skip no description.
        val commentContent = node.findFirstToken(COMMENT_CONTENT) ?: return
        if (commentContent.text.isBlank()) {
            return
        }

        // Checks for violation.
        val punctuation = commentContent.text.trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            log(commentContent, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "end.sentence.with.period.comment"

        private val END_PUNCTUATIONS = setOf('.', ')')
    }
}
