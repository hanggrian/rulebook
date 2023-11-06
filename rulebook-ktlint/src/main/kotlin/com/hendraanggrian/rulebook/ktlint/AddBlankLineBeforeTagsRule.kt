package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#add-blank-line-before-tags).
 */
class AddBlankLineBeforeTagsRule : RulebookRule("add-blank-line-before-tags") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != KDOC_SECTION) {
            return
        }

        // only allow first tag
        val kdocTag = node.findChildByType(KDOC_TAG) ?: return
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
            emit(kdocTag.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "add.blank.line.before.tags"

        private val ASTNode.prevKdocLeadingAsterisk: ASTNode?
            get() = prevSibling { it.elementType == KDOC_LEADING_ASTERISK }
    }
}
