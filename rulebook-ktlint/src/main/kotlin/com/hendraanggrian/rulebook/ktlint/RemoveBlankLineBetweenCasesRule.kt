package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#remove-blank-line-between-cases).
 */
class RemoveBlankLineBetweenCasesRule : RulebookRule("remove-blank-line-between-cases") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // First line of filter.
        if (node.elementType != WHEN_ENTRY) {
            return
        }

        // Get previous whitespace.
        val whitespace = node.treePrev
        if (whitespace.elementType != WHITE_SPACE) {
            return
        }

        // Report missing empty line.
        if ("\n\n" in whitespace.text) {
            emit(whitespace.endOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "remove.blank.line.between.cases"
    }
}
