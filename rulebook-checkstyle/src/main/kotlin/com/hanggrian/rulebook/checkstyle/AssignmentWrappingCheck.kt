package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#assignment-wrapping) */
public class AssignmentWrappingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(ASSIGN)

    override fun visitToken(node: DetailAST) {
        // target multiline assignment
        val expr =
            (node.findFirstToken(EXPR) ?: node.findFirstToken(DOT)?.nextSibling)
                ?.takeIf { it.isMultiline() }
                ?: return

        // checks for violation
        expr
            .takeUnless { it.minLineNo == node.lineNo + 1 }
            ?: return
        log(expr, Messages[MSG])
    }

    internal companion object {
        const val MSG = "assignment.wrapping"
    }
}
