package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-comment-trim) */
public class BlockCommentTrimRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(KDOC_SECTION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // initial node is always asterisk
        val firstChild = node.firstChildNode.takeIf { it.elementType == KDOC_LEADING_ASTERISK }
        if (firstChild?.treeNext?.isWhiteSpace() == true) {
            emit(firstChild.startOffset, Messages[MSG_FIRST], false)
        }

        // final node may be asterisk or tag
        val lastChild =
            node
                .lastChildNode
                .let { n ->
                    when (n.elementType) {
                        KDOC_LEADING_ASTERISK -> n
                        KDOC_TAG ->
                            n
                                .findChildByType(KDOC_LEADING_ASTERISK)
                                ?.takeIf { n.lastChildNode === it }

                        else -> null
                    }
                }?.takeIf { it.treePrev?.isWhiteSpace() == true }
                ?: return
        emit(lastChild.treePrev!!.endOffset, Messages[MSG_LAST], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-comment-trim")

        private const val MSG_FIRST = "block.comment.trim.first"
        private const val MSG_LAST = "block.comment.trim.last"
    }
}
