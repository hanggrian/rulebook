package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.firstOrNull
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_SECTION
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import com.pinterest.ktlint.core.ast.ElementType.WHITE_SPACE
import com.pinterest.ktlint.core.ast.children
import com.pinterest.ktlint.core.ast.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See Guide](https://github.com/hendraanggrian/lints/blob/main/rules.md#tag-group-starting-empty-line).
 */
class TagGroupStartingEmptyLineRule : Rule("tag-group-starting-empty-line") {
    internal companion object {
        const val ERROR_MESSAGE = "Missing empty line before '%s'."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != KDOC_SECTION) {
            return
        }

        // find first tag
        val kdocTag = (node firstOrNull KDOC_TAG) ?: return
        if (kdocTag.prevSibling { it.elementType == KDOC_TAG } != null) {
            return
        }

        // gather list of previous leading asterisks
        val prevKdocLeadingAsterisks = mutableListOf<ASTNode>()
        val kdocTagAsterisk =
            kdocTag.prevSibling { it.elementType == KDOC_LEADING_ASTERISK } ?: return
        var prev = kdocTagAsterisk.treePrev
        while (prev != null) {
            if (prev.elementType == KDOC_LEADING_ASTERISK) {
                prevKdocLeadingAsterisks += prev
            }
            prev = prev.treePrev
        }

        // pass if there is no summary
        if (prevKdocLeadingAsterisks.isEmpty()) {
            return
        }

        // find empty line
        val hasEmptyLine = prevKdocLeadingAsterisks.any { prevKdocLeadingAsterisk ->
            val siblings = prevKdocLeadingAsterisk.siblingsUntilNextLeadingAsterisk
            siblings.size == 1 && siblings.single().elementType == WHITE_SPACE
        }
        if (!hasEmptyLine) {
            val tagName = kdocTag.children().first().text
            emit(kdocTag.startOffset, ERROR_MESSAGE.format(tagName), false)
        }
    }

    private val ASTNode.siblingsUntilNextLeadingAsterisk: List<ASTNode>
        get() {
            require(elementType == KDOC_LEADING_ASTERISK)
            val siblings = mutableListOf<ASTNode>()
            var next = treeNext
            while (next != null && next.elementType != KDOC_LEADING_ASTERISK) {
                siblings += next
                next = next.treeNext
            }
            return siblings
        }
}
