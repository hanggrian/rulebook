package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.nextSibling
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#block-comment-line-trimming)
 */
public class BlockCommentLineTrimmingRule : Rule("block-comment-line-trimming") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != KDOC_SECTION) {
            return
        }

        // initial node is always asterisk
        val firstChild = node.children().first().takeIf { it.elementType == KDOC_LEADING_ASTERISK }
        if (firstChild?.nextSibling()?.elementType == WHITE_SPACE) {
            emit(firstChild.startOffset, Messages[MSG_FIRST], false)
        }

        // final node may be asterisk or tag
        val lastChild =
            node.children().last().let {
                when (it.elementType) {
                    KDOC_LEADING_ASTERISK -> it
                    KDOC_TAG -> it.findChildByType(KDOC_LEADING_ASTERISK)
                    else -> null
                }
            }
        if (lastChild?.prevSibling()?.elementType == WHITE_SPACE) {
            emit(lastChild.prevSibling()!!.endOffset, Messages[MSG_LAST], false)
        }
    }

    internal companion object {
        const val MSG_FIRST = "block.comment.line.trimming.first"
        const val MSG_LAST = "block.comment.line.trimming.last"
    }
}
