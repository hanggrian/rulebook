package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.hendraanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.utils.addToStdlib.ifFalse

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#block-comment-spacing).
 */
public class BlockCommentSpacingRule : RulebookRule("block-comment-spacing") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != KDOC_SECTION &&
            node.elementType != KDOC_LEADING_ASTERISK
        ) {
            return
        }

        // determine if single line
        when (node.elementType) {
            KDOC_SECTION -> {
                // only target single line
                node.treeParent.takeUnless { '\n' in it.text } ?: return

                // checks for violation
                node.text.startsWith(' ')
                    .ifFalse { emit(node.startOffset, Messages[MSG_LINE_START], false) }
                when {
                    KDOC_TAG in node -> node.treeNext.elementType == WHITE_SPACE
                    else -> node.text.endsWith(' ')
                }.ifFalse { emit(node.endOffset, Messages[MSG_LINE_END], false) }
            }
            else -> {
                // checks for violation
                node.siblingsUntil(KDOC_LEADING_ASTERISK)
                    .takeUnless { n ->
                        n.all {
                            it.elementType == KDOC_LEADING_ASTERISK ||
                                it.elementType == WHITE_SPACE
                        }
                    }
                    ?.joinToString("") { it.text }
                    ?.takeUnless { it.startsWith(' ') }
                    ?: return
                emit(node.endOffset, Messages[MSG_MULTILINE_START], false)
            }
        }
    }

    internal companion object {
        const val MSG_LINE_START = "block.comment.spacing.line.start"
        const val MSG_LINE_END = "block.comment.spacing.line.end"
        const val MSG_MULTILINE_START = "block.comment.spacing.multiline.start"
    }
}
