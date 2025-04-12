package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment) */
public class DuplicateBlankLineInCommentRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(EOL_COMMENT)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // no comment with content
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

    public companion object {
        public val ID: RuleId =
            RuleId("${RulebookRuleSet.ID.value}:duplicate-blank-line-in-comment")

        private const val MSG = "duplicate.blank.line.in.comment"
    }
}
