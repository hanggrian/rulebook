package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-joining) */
public class CommentLineJoiningRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(EOL_COMMENT)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip comment with content
        node
            .takeIf { it.isEolCommentEmpty() }
            ?: return

        // find matching sibling
        val next =
            node
                .treeNext
                ?.takeIf { it.isWhitespaceSingleLine() }
                ?.treeNext
                ?.takeIf { it.elementType == EOL_COMMENT }
                ?: return

        // checks for violation
        next
            .takeIf { it.isEolCommentEmpty() }
            ?: return
        emit(next.endOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:comment-line-joining")

        const val MSG = "comment.line.joining"
    }
}
