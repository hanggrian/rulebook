package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.lastmostChild
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CASE_GROUP

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-joining)
 */
public class CaseLineJoiningCheck : Check() {
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
        const val MSG = "case.line.joining"
    }
}
