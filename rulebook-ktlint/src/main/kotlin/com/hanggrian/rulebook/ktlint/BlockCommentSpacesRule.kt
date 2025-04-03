package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-comment-spaces) */
public class BlockCommentSpacesRule : RulebookRule(ID) {
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

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-comment-spaces")

        private const val MSG_SINGLE_START = "block.comment.spaces.single.start"
        private const val MSG_SINGLE_END = "block.comment.spaces.single.end"
        private const val MSG_MULTI = "block.comment.spaces.multi"
    }
}
