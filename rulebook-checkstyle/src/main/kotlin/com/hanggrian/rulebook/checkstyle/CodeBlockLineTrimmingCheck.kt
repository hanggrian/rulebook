package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.lastMostChild
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.BLOCK_COMMENT_BEGIN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#code-block-line-trimming)
 */
public class CodeBlockLineTrimmingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // right brace is persistent
        val rightBrace = node.lastChild.takeIf { it.type == RCURLY } ?: return
        val rightBraceSibling = rightBrace.previousSibling?.lastMostChild ?: return

        // left brace is conditional
        val (leftBrace, leftBraceSibling) =
            when (node.type) {
                OBJBLOCK -> {
                    // get first two nodes to compare
                    val leftBrace = node.firstChild.takeIf { it.type == LCURLY } ?: return
                    val leftBraceSibling = leftBrace.nextSibling?.orBlockComment ?: return
                    leftBrace to leftBraceSibling
                }
                else ->
                    // the first node is slist itself
                    node.firstChild
                        ?.let { node to it }
                        ?: return
            }

        // checks for violation
        if (leftBraceSibling.lineNo - leftBrace.lineNo > 1) {
            log(leftBraceSibling, Messages[MSG_FIRST])
        }
        if (rightBrace.lineNo - rightBraceSibling.lineNo > 1) {
            log(rightBraceSibling, Messages[MSG_LAST])
        }
    }

    internal companion object {
        const val MSG_FIRST = "code.block.line.trimming.first"
        const val MSG_LAST = "code.block.line.trimming.last"

        private val DetailAST.orBlockComment: DetailAST
            get() =
                findFirstToken(MODIFIERS)
                    ?.findFirstToken(BLOCK_COMMENT_BEGIN)
                    ?: this
    }
}
