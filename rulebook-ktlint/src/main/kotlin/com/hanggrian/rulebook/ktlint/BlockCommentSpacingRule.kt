package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-spacing) */
public class BlockCommentSpacingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_SECTION, KDOC_LEADING_ASTERISK)

    override fun visitToken(node: ASTNode, emit: Emit) {
        when (node.elementType) {
            KDOC_SECTION -> {
                // only target single line
                node
                    .treeParent
                    .takeUnless { '\n' in it.text }
                    ?: return

                // checks for violation
                if (!node.text.startsWith(' ')) {
                    emit(node.startOffset, Messages[MSG_SINGLE_START], false)
                }
                if (!node.text.endsWith(' ')) {
                    emit(node.endOffset, Messages[MSG_SINGLE_END], false)
                }
            }

            else -> {
                // take non-whitespace sibling
                val next =
                    node
                        .treeNext
                        ?.takeUnless { it.isWhiteSpace() }
                        ?: return

                // checks for violation
                next
                    .takeUnless { it.text.startsWith(' ') }
                    ?: return
                emit(next.startOffset, Messages[MSG_MULTI], false)
            }
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:block-comment-spacing")

        const val MSG_SINGLE_START = "block.comment.spacing.single.start"
        const val MSG_SINGLE_END = "block.comment.spacing.single.end"
        const val MSG_MULTI = "block.comment.spacing.multi"
    }
}
