package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#switch-entry-whitespace).
 */
class SwitchEntryWhitespaceRule : RulebookRule("switch-entry-whitespace") {
    internal companion object {
        const val ERROR_MESSAGE = "Unexpected empty line."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != WHEN) {
            return
        }

        // only keep entries
        val whenEntries = node.children().filter { it.elementType == WHEN_ENTRY }.toList()
        if (whenEntries.isEmpty()) {
            return
        }

        // check leading newlines
        whenEntries.forEach {
            val whitespace = it.treePrev
            if (whitespace.elementType != WHITE_SPACE) {
                return
            }
            if ("\n\n" in whitespace.text) {
                emit(whitespace.endOffset, ERROR_MESSAGE, false)
            }
        }
    }
}
