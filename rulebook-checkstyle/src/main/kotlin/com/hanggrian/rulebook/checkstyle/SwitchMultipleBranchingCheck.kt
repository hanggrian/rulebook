package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_SWITCH

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-multiple-branching)
 */
public class SwitchMultipleBranchingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_SWITCH)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node
            .children()
            .filter { it.type == CASE_GROUP }
            .takeIf { it.count() < 2 }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "switch.multiple.branching"
    }
}
