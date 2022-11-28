package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.endOffset
import com.hendraanggrian.lints.ktlint.get
import com.hendraanggrian.lints.ktlint.siblingsUntil
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/lints/blob/main/rules.md#summary-continuation).
 */
class SummaryContinuationRule : Rule("summary-continuation") {
    internal companion object {
        const val ERROR_MESSAGE = "First word of paragraph continuation cannot be a '%s'."
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

        // skips first line of paragraph
        if (node.treeParent[KDOC_LEADING_ASTERISK] == node) {
            return
        }

        // skips if tag is found
        val kdocLeadingAsteriskLine = node.siblingsUntil(KDOC_LEADING_ASTERISK)
        if (KDOC_TAG in kdocLeadingAsteriskLine.map { it.elementType }) {
            return
        }

        // check the first word of paragraph continuation
        val line = kdocLeadingAsteriskLine.joinToString("") { it.text }.trimStart()
        if (line.startsWith('`') && !line.startsWith("```")) {
            emit(node.endOffset, ERROR_MESSAGE.format("code"), false)
        } else if (line.startsWith('[')) {
            emit(node.endOffset, ERROR_MESSAGE.format("link"), false)
        }
    }
}
