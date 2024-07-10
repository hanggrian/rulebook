package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleNewline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-joining)
 */
public class CommentLineJoiningRule :
    Rule("comment-line-joining"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != EOL_COMMENT) {
            return
        }

        // skip comment with content
        node.takeIf { it.isEolCommentEmpty() } ?: return

        // find matching sibling
        val next =
            node.treeNext
                ?.takeIf { it.isWhitespaceSingleNewline() }
                ?.treeNext
                ?.takeIf { it.elementType == EOL_COMMENT }
                ?: return

        // checks for violation
        next.takeIf { it.isEolCommentEmpty() } ?: return
        emit(next.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "comment.line.joining"
    }
}
