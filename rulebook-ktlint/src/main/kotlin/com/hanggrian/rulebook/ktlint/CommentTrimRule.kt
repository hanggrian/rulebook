package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#comment-trim) */
public class CommentTrimRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(EOL_COMMENT)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // continue if this comment is first line
        node
            .treePrev
            .takeUnless {
                it?.isWhitespaceSingleLine() == true &&
                    it.treePrev?.elementType == EOL_COMMENT
            } ?: return

        // iterate to find last
        var current = node
        while (current.treeNext?.isWhitespaceSingleLine() == true &&
            current.treeNext?.treeNext?.elementType == EOL_COMMENT
        ) {
            current = current.treeNext.treeNext
        }

        // skip blank comment
        current
            .takeUnless { it === node }
            ?: return

        // checks for violation
        if (node.isEolCommentEmpty()) {
            emit(node.startOffset, Messages[MSG], false)
        }
        if (current.isEolCommentEmpty()) {
            emit(current.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:comment-trim")

        const val MSG = "comment.trim"
    }
}
