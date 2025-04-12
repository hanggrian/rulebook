package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.firstLeaf
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrap) */
public class AssignmentWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(ASSIGN)

    override fun visitToken(node: DetailAST) {
        // no lambda initializers
        node
            .takeUnless { LAMBDA in it }
            ?: return

        // find multiline assignee
        val expr =
            (node.findFirstToken(EXPR) ?: node.findFirstToken(DOT)?.nextSibling)
                ?.takeIf { it.isMultiline() }
                ?: return

        // checks for violation
        expr
            .minLineNo
            .takeIf { it == node.lineNo }
            ?: return
        log(expr.firstLeaf(), Messages[MSG])
    }

    private companion object {
        const val MSG = "assignment.wrap"
    }
}
