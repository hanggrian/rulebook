package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import kotlin.text.RegexOption.IGNORE_CASE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#todo-comment-formatting).
 */
public class TodoCommentFormattingRule : RulebookRule("todo-comment-formatting") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != EOL_COMMENT &&
            node.elementType != KDOC_LEADING_ASTERISK &&
            node.elementType != KDOC_SECTION
        ) {
            return
        }

        // multiline comment is checked line-by-line
        val text =
            when (node.elementType) {
                KDOC_LEADING_ASTERISK ->
                    node.siblingsUntil(KDOC_LEADING_ASTERISK)
                        .takeUnless { n ->
                            n.all {
                                it.elementType == KDOC_LEADING_ASTERISK ||
                                    it.elementType == WHITE_SPACE
                            }
                        }
                        ?.joinToString("") { it.text }
                        ?: return
                else -> node.text
            }

        // checks for violation
        KEYWORD_REGEX.find(text)
            ?.let { emit(node.startOffset, Messages.get(MSG_KEYWORD, it.value), false) }
        SEPARATOR_REGEX.find(text)
            ?.let { emit(node.startOffset, Messages.get(MSG_SEPARATOR, it.value.last()), false) }
    }

    internal companion object {
        const val MSG_KEYWORD = "todo.comment.formatting.keyword"
        const val MSG_SEPARATOR = "todo.comment.formatting.separator"

        private val KEYWORD_REGEX = Regex("\\b(?i:fixme|todo)(?<!FIXME|TODO)\\b")
        private val SEPARATOR_REGEX = Regex("\\b(todo|fixme)\\S", IGNORE_CASE)
    }
}
