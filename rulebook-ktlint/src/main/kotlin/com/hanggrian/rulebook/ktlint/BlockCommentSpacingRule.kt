package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-spacing)
 */
public class BlockCommentSpacingRule :
    Rule("block-comment-spacing"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        when (node.elementType) {
            KDOC_SECTION -> {
                // only target single line
                node.treeParent.takeUnless { '\n' in it.text } ?: return

                // checks for violation
                if (!node.text.startsWith(' ')) {
                    emit(node.startOffset, Messages[MSG_SINGLE_START], false)
                }
                if (!node.text.endsWith(' ')) {
                    emit(node.endOffset, Messages[MSG_SINGLE_END], false)
                }
            }
            KDOC_LEADING_ASTERISK -> {
                // take non-whitespace sibling
                val next = node.treeNext?.takeUnless { it.elementType == WHITE_SPACE } ?: return

                // checks for violation
                next.takeUnless { it.text.startsWith(' ') } ?: return
                emit(next.startOffset, Messages[MSG_MULTI], false)
            }
        }
    }

    internal companion object {
        const val MSG_SINGLE_START = "block.comment.spacing.single.start"
        const val MSG_SINGLE_END = "block.comment.spacing.single.end"
        const val MSG_MULTI = "block.comment.spacing.multi"
    }
}
