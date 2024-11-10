package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#code-block-line-trimming) */
public class CodeBlockLineTrimmingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // right brace is persistent
        val rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return
        val rcurlySibling = rcurly.previousSibling ?: return

        // left brace is conditional
        val (lcurly, lcurlySibling) =
            when (node.type) {
                OBJBLOCK -> {
                    // get first two nodes to compare
                    val lcurly = node.firstChild.takeIf { it.type == LCURLY } ?: return
                    val lcurlySibling = lcurly.nextSibling ?: return
                    lcurly to lcurlySibling
                }

                else ->
                    // the first node is slist itself
                    node to node.firstChild
            }

        // checks for violation
        if (lcurlySibling.minLineNo - lcurly.lineNo > 1) {
            log(lcurlySibling, Messages[MSG_FIRST])
        }
        if (rcurly.lineNo - rcurlySibling.maxLineNo > 1) {
            log(rcurlySibling, Messages[MSG_LAST])
        }
    }

    internal companion object {
        const val MSG_FIRST = "code.block.line.trimming.first"
        const val MSG_LAST = "code.block.line.trimming.last"
    }
}
