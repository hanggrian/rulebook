package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.isWhitespaceSingleLine
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
                    it.treePrev?.elementType === EOL_COMMENT
            } ?: return

        // iterate to find last
        var current = node
        while (current.treeNext?.isWhitespaceSingleLine() == true &&
            current.treeNext?.treeNext?.elementType === EOL_COMMENT
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

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:comment-trim")

        private const val MSG = "comment.trim"
    }
}
