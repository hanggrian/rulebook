package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-comment-line-joining)
 */
public class EmptyCommentLineJoiningRule : Rule("empty-comment-line-joining") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != EOL_COMMENT) {
            return
        }

        // skip comment with content
        node.takeIf { it.isEolCommentEmpty() } ?: return

        // find matching sibling
        val next =
            node.treeNext?.takeIf { it.isWhiteSpaceWithNewline() }
                ?.treeNext?.takeIf { it.elementType == EOL_COMMENT }
                ?: return

        // checks for violation
        next.takeIf { it.isEolCommentEmpty() } ?: return
        emit(next.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "empty.comment.line.joining"

        private fun ASTNode.isEolCommentEmpty(): Boolean = text.trim().replace("/", "").isEmpty()
    }
}
