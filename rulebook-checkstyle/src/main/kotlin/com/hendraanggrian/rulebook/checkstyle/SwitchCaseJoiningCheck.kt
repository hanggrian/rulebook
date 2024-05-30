package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#switch-case-joining)
 */
public class SwitchCaseJoiningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(CASE_GROUP)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node.previousSibling
            ?.takeIf { it.type == CASE_GROUP }
            ?.takeUnless { it.lastmostChild.lineNo + 1 == node.firstChild.lineNo }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "switch.case.joining"

        val DetailAST.lastmostChild: DetailAST
            get() {
                var last = lastChild
                while (last.lastChild != null) {
                    last = last.lastChild
                }
                return last
            }
    }
}
