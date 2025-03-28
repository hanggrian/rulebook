package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.DESCRIPTION
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.EOF
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.JAVADOC_TAG
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.LEADING_ASTERISK
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.NEWLINE
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.TEXT
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.WS

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#block-comment-trim) */
public class BlockCommentTrimCheck : RulebookJavadocCheck() {
    override fun getDefaultJavadocTokens(): IntArray = intArrayOf(JAVADOC)

    override fun visitJavadocToken(node: DetailNode) {
        // allow blank comment
        node
            .children
            .filterNot { it.type == LEADING_ASTERISK || it.type == EOF }
            .joinToString("") { it.text }
            .takeUnless { it.isBlank() }
            ?: return

        // initial node is always newline
        var children = node.filterEmpty()
        val firstChild = children.first().takeIf { it.type == NEWLINE }
        if (firstChild != null &&
            children.indexOf(firstChild).let {
                children.getOrNull(it + 1)?.type == LEADING_ASTERISK &&
                    children.getOrNull(it + 2)?.type == NEWLINE
            }
        ) {
            log(firstChild.lineNumber, firstChild.columnNumber, Messages[MSG_FIRST])
        }

        // final node may be newline or tag
        val lastChild =
            children
                .last { it.type != EOF }
                .let { child ->
                    when (child.type) {
                        NEWLINE -> child
                        JAVADOC_TAG -> {
                            val description =
                                child.children.firstOrNull { it.type == DESCRIPTION }
                                    ?: return@let null
                            children = description.filterEmpty()
                            return@let children.lastOrNull { it.type == NEWLINE }
                        }

                        else -> null
                    }
                }?.takeIf { n ->
                    children.indexOf(n).let {
                        children.getOrNull(it - 1)?.type == LEADING_ASTERISK &&
                            children.getOrNull(it - 2)?.type == NEWLINE
                    }
                } ?: return
        log(lastChild.lineNumber, lastChild.columnNumber, Messages[MSG_LAST])
    }

    internal companion object {
        const val MSG_FIRST = "block.comment.trim.first"
        const val MSG_LAST = "block.comment.trim.last"

        /** Disregard whitespace between asterisk and newline. */
        private fun DetailNode.filterEmpty() =
            children.filter {
                when (it.type) {
                    TEXT -> it.text.isNotBlank()
                    WS -> false
                    else -> true
                }
            }
    }
}
