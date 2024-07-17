package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpace
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-line-trimming)
 */
public class BlockCommentLineTrimmingRule : Rule("block-comment-line-trimming") {
    override val tokens: TokenSet = TokenSet.create(KDOC_SECTION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // initial node is always asterisk
        val firstChild = node.firstChildNode.takeIf { it.elementType == KDOC_LEADING_ASTERISK }
        if (firstChild?.treeNext?.isWhiteSpace() == true) {
            emit(firstChild.startOffset, Messages[MSG_FIRST], false)
        }

        // final node may be asterisk or tag
        val lastChild =
            node.lastChildNode
                .let {
                    when (it.elementType) {
                        KDOC_LEADING_ASTERISK -> it
                        KDOC_TAG -> it.findChildByType(KDOC_LEADING_ASTERISK)
                        else -> null
                    }
                }?.takeIf { it.treePrev?.isWhiteSpace() == true }
                ?: return
        emit(lastChild.treePrev!!.endOffset, Messages[MSG_LAST], false)
    }

    internal companion object {
        const val MSG_FIRST = "block.comment.line.trimming.first"
        const val MSG_LAST = "block.comment.line.trimming.last"
    }
}
