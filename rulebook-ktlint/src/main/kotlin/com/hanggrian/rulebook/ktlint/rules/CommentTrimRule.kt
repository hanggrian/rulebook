package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.isEolCommentEmpty
import com.hanggrian.rulebook.ktlint.isWhitespaceSingleLine
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.nextSibling20
import com.pinterest.ktlint.rule.engine.core.api.prevSibling20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#comment-trim) */
public class CommentTrimRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(EOL_COMMENT)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // continue if this comment is first line
        node
            .prevSibling20
            ?.takeUnless {
                it.isWhitespaceSingleLine() &&
                    it.prevSibling20?.elementType === EOL_COMMENT
            } ?: return

        // iterate to find last
        var current = node
        while (current.nextSibling20?.isWhitespaceSingleLine() == true &&
            current.nextSibling20?.nextSibling20?.elementType === EOL_COMMENT
        ) {
            current = current.nextSibling20!!.nextSibling20!!
        }

        // skip blank comment
        current
            .takeUnless { it === node }
            ?: return

        // checks for violation
        node
            .takeIf { it.isEolCommentEmpty() }
            ?.run { emit(startOffset, Messages[MSG], false) }
        current
            .takeIf { it.isEolCommentEmpty() }
            ?.run { emit(startOffset, Messages[MSG], false) }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:comment-trim")
        private const val MSG = "comment.trim"
    }
}
