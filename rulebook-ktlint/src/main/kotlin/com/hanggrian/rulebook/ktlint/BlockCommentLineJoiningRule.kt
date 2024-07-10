package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-line-joining)
 */
public class BlockCommentLineJoiningRule :
    Rule("block-comment-line-joining"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != KDOC_LEADING_ASTERISK) {
            return
        }

        // find matching sibling
        val next =
            node.treeNext
                ?.takeIf { it.isWhiteSpaceWithNewline() }
                ?.treeNext
                ?.takeIf { it.elementType == KDOC_LEADING_ASTERISK }
                ?: return

        // checks for violation
        next.treeNext?.takeIf { it.isWhiteSpaceWithNewline() } ?: return
        emit(next.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "block.comment.line.joining"
    }
}
