package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import kotlin.text.RegexOption.IGNORE_CASE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#todo-comment-listing).
 */
public class TodoCommentListingRule : RulebookRule("todo-comment-listing") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        val text =
            when (node.elementType) {
                EOL_COMMENT -> node.text
                KDOC_LEADING_ASTERISK ->
                    node.siblingsUntil(KDOC_LEADING_ASTERISK)
                        .takeUnless { n ->
                            n.all {
                                it.elementType == KDOC_LEADING_ASTERISK ||
                                    it.elementType == WHITE_SPACE
                            }
                        }
                        ?.joinToString("") { it.text }
                else -> null
            } ?: return

        // checks for violation
        when {
            LOWERCASE_REGEX.containsMatchIn(text) ->
                emit(node.startOffset, Messages[MSG_LOWERCASE], false)
            UNKNOWN_REGEX.containsMatchIn(text) ->
                emit(node.startOffset, Messages[MSG_UNKNOWN], false)
        }
    }

    internal companion object {
        const val MSG_LOWERCASE = "todo.comment.listing.lowercase"
        const val MSG_UNKNOWN = "todo.comment.listing.unknown"

        private val LOWERCASE_REGEX = Regex("(todo|fixme):")
        private val UNKNOWN_REGEX = Regex("(TO-DO|FIX):", IGNORE_CASE)
    }
}
