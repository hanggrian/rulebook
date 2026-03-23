package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
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

    override fun visit(node: DetailAST) {
        // find multiline assignee
        val child =
            (
                node.findFirstToken(DOT)?.nextSibling
                    ?: node.findFirstToken(EXPR)
                    ?: node.findFirstToken(LAMBDA)
            )?.takeIf { it.isMultiline() }
                ?: return

        // checks for violation
        child
            .takeIf { it.type == LAMBDA }
            ?.let {
                child
                    .takeUnless { it.minLineNo == node.lineNo }
                    ?: return
                log(child.firstLeaf(), Messages[MSG_UNEXPECTED])
                return
            }
        child
            .takeUnless { it.minLineNo == node.lineNo + 1 }
            ?: return
        log(child.firstLeaf(), Messages[MSG_MISSING])
    }

    private companion object {
        const val MSG_MISSING = "assignment.wrap.missing"
        const val MSG_UNEXPECTED = "assignment.wrap.unexpected"
    }
}
