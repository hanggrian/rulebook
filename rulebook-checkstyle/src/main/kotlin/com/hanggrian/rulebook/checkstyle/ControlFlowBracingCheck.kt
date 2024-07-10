package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.lastmostChild
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_DO
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_FOR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_WHILE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#control-flow-bracing)
 */
public class ControlFlowBracingCheck : Check() {
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
        node.takeUnless { it.lineNo == node.lastmostChild.lineNo } ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "control.flow.bracing"

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
    }
}
