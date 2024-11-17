package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CASE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-case-branching) */
public class SwitchCaseBranchingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun visitToken(node: DetailAST) {
        // target single entry
        val case =
            node
                .children
                .singleOrNull { it.type == CASE_GROUP }
                ?: return

        // checks for violation
        case
            .children
            .takeUnless { cases2 -> cases2.count { it.type == LITERAL_CASE } > 1 }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "switch.case.branching"
    }
}
