package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-code-block-wrapping)
 */
public class EmptyCodeBlockWrappingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // additional filter
        val lcurly: DetailAST
        val rcurly: DetailAST
        when (node.type) {
            OBJBLOCK -> {
                // skip non-empty content
                node.takeUnless { it.childCount > 2 } ?: return

                // class block have left and right braces
                lcurly = node.firstChild.takeIf { it.type == LCURLY } ?: return
                rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return
            }
            else -> {
                // skip non-empty content
                node.takeUnless { it.childCount > 1 } ?: return

                // function block only have right brace
                lcurly = node
                rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return
            }
        }

        // checks for violation
        lcurly.takeUnless { it.lineNo == rcurly.lineNo && lcurly.columnNo + 1 == rcurly.columnNo }
            ?: return
        log(lcurly, Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.code.block.wrapping"
    }
}
