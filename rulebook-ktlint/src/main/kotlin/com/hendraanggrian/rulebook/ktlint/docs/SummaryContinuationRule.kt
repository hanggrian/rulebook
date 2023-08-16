package com.hendraanggrian.rulebook.ktlint.docs

import com.hendraanggrian.rulebook.ktlint.Messages
import com.hendraanggrian.rulebook.ktlint.RulebookRule
import com.hendraanggrian.rulebook.ktlint.endOffset
import com.hendraanggrian.rulebook.ktlint.get
import com.hendraanggrian.rulebook.ktlint.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/SummaryContinuation).
 */
class SummaryContinuationRule : RulebookRule("summary-continuation") {
    internal companion object {
        const val MSG_CODE = "summary.continuation.code"
        const val MSG_LINK = "summary.continuation.link"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != KDOC_LEADING_ASTERISK) {
            return
        }

        // skip first line of paragraph
        if (node.treeParent[KDOC_LEADING_ASTERISK] == node) {
            return
        }

        // skip if tag is found
        val kdocLeadingAsteriskLine = node.siblingsUntil(KDOC_LEADING_ASTERISK)
        if (KDOC_TAG in kdocLeadingAsteriskLine.map { it.elementType }) {
            return
        }

        // check the first word of paragraph continuation
        val line = kdocLeadingAsteriskLine.joinToString("") { it.text }.trimStart()
        if (line.startsWith('`') && !line.startsWith("```")) {
            emit(node.endOffset, Messages[MSG_CODE], false)
        } else if (line.startsWith('[')) {
            emit(node.endOffset, Messages[MSG_LINK], false)
        }
    }
}
