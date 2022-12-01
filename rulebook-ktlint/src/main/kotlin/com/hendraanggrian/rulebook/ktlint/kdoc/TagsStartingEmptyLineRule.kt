package com.hendraanggrian.rulebook.ktlint.kdoc

import com.hendraanggrian.rulebook.ktlint.firstOrNull
import com.hendraanggrian.rulebook.ktlint.siblingsUntil
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_SECTION
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import com.pinterest.ktlint.core.ast.children
import com.pinterest.ktlint.core.ast.isWhiteSpaceWithNewline
import com.pinterest.ktlint.core.ast.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#tags-starting-empty-line).
 */
class TagsStartingEmptyLineRule : Rule("tags-starting-empty-line") {
    internal companion object {
        const val ERROR_MESSAGE = "Missing empty line before '%s'."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != KDOC_SECTION) {
            return
        }

        // only allow first tag
        val kdocTag = (node firstOrNull KDOC_TAG) ?: return
        if (kdocTag.prevSibling { it.elementType == KDOC_TAG } != null) {
            return
        }

        // find previous leading asterisk
        val kdocTagAsterisk = kdocTag.prevKdocLeadingAsterisk ?: return
        val prevKdocTagLeadingAsterisk = kdocTagAsterisk.prevKdocLeadingAsterisk ?: return

        // check if last line is newline
        val siblings = prevKdocTagLeadingAsterisk.siblingsUntil(KDOC_LEADING_ASTERISK)
        val hasEmptyLine = siblings.size == 1 && siblings.single().isWhiteSpaceWithNewline()
        if (!hasEmptyLine) {
            val tagName = kdocTag.children().first().text
            emit(kdocTag.startOffset, ERROR_MESSAGE.format(tagName), false)
        }
    }

    private val ASTNode.prevKdocLeadingAsterisk: ASTNode?
        get() = prevSibling { it.elementType == KDOC_LEADING_ASTERISK }
}
