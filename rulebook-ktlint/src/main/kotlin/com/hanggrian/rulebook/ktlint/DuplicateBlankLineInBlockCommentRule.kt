package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-block-comment) */
public class DuplicateBlankLineInBlockCommentRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_LEADING_ASTERISK)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find matching sibling
        val nextKdocLeadingAsterisk =
            node
                .treeNext
                ?.takeIf { it.isWhiteSpaceWithNewline() }
                ?.treeNext
                ?.takeIf { it.elementType == KDOC_LEADING_ASTERISK }
                ?: return

        // checks for violation
        nextKdocLeadingAsterisk
            .takeIf { it.treeNext.isWhiteSpaceWithNewline() }
            ?: return
        emit(nextKdocLeadingAsterisk.endOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:duplicate-blank-line-in-block-comment")

        const val MSG = "duplicate.blank.line.in.block.comment"
    }
}
