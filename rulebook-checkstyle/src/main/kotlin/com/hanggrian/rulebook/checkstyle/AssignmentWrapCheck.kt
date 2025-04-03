package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrap) */
public class AssignmentWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(ASSIGN)

    override fun visitToken(node: DetailAST) {
        // target multiline assignment
        val expr =
            (
                node.findFirstToken(EXPR)
                    ?: node.findFirstToken(DOT)?.nextSibling
                    ?: node.findFirstToken(LAMBDA)?.findFirstToken(SLIST)
            )?.takeIf { it.isMultiline() }
                ?: return

        // checks for violation
        expr
            .takeUnless { it.minLineNo == node.lineNo + 1 }
            ?: return
        log(expr, Messages[MSG])
    }

    private companion object {
        const val MSG = "assignment.wrap"
    }
}
