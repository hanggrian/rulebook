package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.ClassFinalNameBlacklistingCheck.Companion.MSG_ALL
import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.contains
import com.hendraanggrian.rulebook.checkstyle.internals.lastmostChild
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_DO
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_FOR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_WHILE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#control-flow-multiline-bracing)
 */
public class ControlFlowMultilineBracingCheck : Check() {
    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            LITERAL_IF,
            LITERAL_FOR,
            LITERAL_WHILE,
            LITERAL_DO,
        )

    override fun visitToken(node: DetailAST) {
        // skip control flows that are already braced
        if (node.type != LITERAL_IF && SLIST in node) {
            return
        }
        if (node.type == LITERAL_IF && node.isIfStatementAllBraced()) {
            return
        }

        // checks for violation
        if (node.lineNo == node.lastmostChild.lineNo) {
            return
        }
        log(node, Messages[MSG_ALL])
    }

    private fun DetailAST.isIfStatementAllBraced(): Boolean {
        var current = this
        while (LITERAL_ELSE in current) {
            if (SLIST !in current) {
                return false
            }

            current = current.findFirstToken(LITERAL_ELSE)
            if (LITERAL_IF in current) {
                current = current.findFirstToken(LITERAL_IF)
            }
        }
        return true
    }

    internal companion object {
        const val MSG = "control.flow.multiline.bracing"
    }
}
