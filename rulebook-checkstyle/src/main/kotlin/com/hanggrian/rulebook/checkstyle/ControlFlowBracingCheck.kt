package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
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
        node
            .takeUnless {
                when (node.type) {
                    LITERAL_IF -> node.isIfStatementAllBraced()
                    else -> SLIST in node
                }
            } ?: return

        // checks for violation
        node
            .takeIf { it.isMultiline() }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "control.flow.bracing"

        private fun DetailAST.isIfStatementAllBraced(): Boolean {
            var current = this
            while (LITERAL_ELSE in current) {
                current =
                    current
                        .takeIf { SLIST in it }
                        ?.findFirstToken(LITERAL_ELSE)
                        ?: return false
                current
                    .findFirstToken(LITERAL_IF)
                    ?.let { current = it }
            }
            return true
        }
    }
}
