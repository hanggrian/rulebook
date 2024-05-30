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

    override fun visitToken(node: DetailAST) {
        // first line of filter
        when (node.type) {
            OBJBLOCK -> {
                // skip non-empty content
                node.takeUnless { it.childCount > 2 } ?: return

                // get braces
                val lcurly = node.firstChild.takeIf { it.type == LCURLY } ?: return
                val rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return

                // checks for violation
                process(lcurly, rcurly)
            }
            SLIST -> {
                // skip non-empty content
                node.takeUnless { it.childCount > 1 } ?: return

                // get braces
                val rcurly = node.lastChild.takeIf { it.type == RCURLY } ?: return

                // checks for violation
                process(node, rcurly)
            }
        }
    }

    private fun process(lcurly: DetailAST, rcurly: DetailAST) {
        lcurly.takeUnless { it.lineNo == rcurly.lineNo && lcurly.columnNo + 1 == rcurly.columnNo }
            ?: return
        log(lcurly, Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.code.block.wrapping"
    }
}
