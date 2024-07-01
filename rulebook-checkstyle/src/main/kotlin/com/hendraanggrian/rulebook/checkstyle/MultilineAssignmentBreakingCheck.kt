package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.firstmostChild
import com.hendraanggrian.rulebook.checkstyle.internals.lastmostChild
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.VARIABLE_DEF

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#multiline-assignment-breaking)
 */
public class MultilineAssignmentBreakingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(VARIABLE_DEF)

    override fun visitToken(node: DetailAST) {
        // target multiline assignment
        val assign =
            node
                .findFirstToken(ASSIGN)
                ?.takeUnless { it.lastmostChild.lineNo == node.lineNo }
                ?: return

        // checks for violation
        val assignee = assign.firstmostChild
        assignee.takeUnless { it.lineNo == assign.lineNo + 1 } ?: return
        log(assignee, Messages[MSG])
    }

    internal companion object {
        const val MSG = "multiline.assignment.breaking"
    }
}
