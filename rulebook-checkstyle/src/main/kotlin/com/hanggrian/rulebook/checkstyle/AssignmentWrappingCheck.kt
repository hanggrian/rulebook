package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.firstMostChild
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#assignment-wrapping)
 */
public class AssignmentWrappingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(VARIABLE_DEF)

    override fun visitToken(node: DetailAST) {
        // target multiline assignment
        val assign =
            node
                .findFirstToken(ASSIGN)
                ?.takeIf { it.isMultiline() }
                ?: return

        // checks for violation
        val expression =
            assign.firstMostChild
                .takeUnless { it.lineNo == assign.lineNo + 1 }
                ?: return
        log(expression, Messages[MSG])
    }

    internal companion object {
        const val MSG = "assignment.wrapping"
    }
}
