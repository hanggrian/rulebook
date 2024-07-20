package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#empty-code-block-conciseness)
 */
public class EmptyCodeBlockConcisenessCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // obtain corresponding braces
        val (leftBrace, rightBrace) =
            when (node.type) {
                OBJBLOCK -> {
                    // skip non-empty content
                    if (node.childCount > 2) {
                        return
                    }

                    // class block have left and right braces
                    val leftBrace = node.firstChild.takeIf { it.type == LCURLY } ?: return
                    val rightBrace = node.lastChild.takeIf { it.type == RCURLY } ?: return
                    leftBrace to rightBrace
                }
                else -> {
                    // skip non-empty content
                    if (node.childCount > 1) {
                        return
                    }

                    // function block only have right brace
                    node.lastChild
                        .takeIf { it.type == RCURLY }
                        ?.let { node to it }
                        ?: return
                }
            }

        // checks for violation
        leftBrace
            .takeUnless {
                it.lineNo == rightBrace.lineNo &&
                    leftBrace.columnNo + 1 == rightBrace.columnNo
            } ?: return
        log(leftBrace, Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.code.block.conciseness"
    }
}
