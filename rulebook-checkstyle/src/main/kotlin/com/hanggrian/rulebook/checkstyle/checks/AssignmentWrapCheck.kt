package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.contains
import com.hanggrian.rulebook.checkstyle.firstLeaf
import com.hanggrian.rulebook.checkstyle.isMultiline
import com.hanggrian.rulebook.checkstyle.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrap) */
public class AssignmentWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(ASSIGN)

    override fun visitToken(node: DetailAST) {
        // skip lambda initializers
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
