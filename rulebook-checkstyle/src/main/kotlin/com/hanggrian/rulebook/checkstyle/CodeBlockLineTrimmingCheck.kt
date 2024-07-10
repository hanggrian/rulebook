package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.lastmostChild
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
        // right curly is persistent
        val rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return
        val rcurlySibling = rcurly.previousSibling?.lastmostChild ?: return

        // left curly is conditional
        val lcurly: DetailAST
        val lcurlySibling: DetailAST
        when (node.type) {
            OBJBLOCK -> {
                // get first two nodes to compare
                lcurly = node.firstChild.takeIf { it.type == LCURLY } ?: return
                lcurlySibling = lcurly.nextSibling?.orBlockComment ?: return
            }
            else -> {
                // the first node is slist itself
                lcurly = node
                lcurlySibling = node.firstChild?.orBlockComment ?: return
            }
        }

        // checks for violation
        if (lcurlySibling.lineNo - lcurly.lineNo > 1) {
            log(lcurlySibling, Messages[MSG_FIRST])
        }
        if (rcurly.lineNo - rcurlySibling.lineNo > 1) {
            log(rcurlySibling, Messages[MSG_LAST])
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
