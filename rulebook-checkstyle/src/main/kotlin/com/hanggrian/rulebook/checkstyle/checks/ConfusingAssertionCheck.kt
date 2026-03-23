package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.contains
import com.hanggrian.rulebook.checkstyle.twoWayMapOf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LNOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NOT_EQUAL

/** [See detail](https://hanggrian.github.io/rulebook/rules/#confusing-assertion) */
public class ConfusingAssertionCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun isTest(): Boolean = true

    override fun visit(node: DetailAST) {
        // find inverted assert function
        val functionReplacement =
            node
                .findFirstToken(IDENT)
                ?.run { ASSERT_CALLS[text] }
                ?: return

        // checks for violation
        node
            .findFirstToken(ELIST)
            ?.findFirstToken(EXPR)
            ?.takeIf { LNOT in it }
            ?: return
        log(node, Messages[MSG, functionReplacement])
    }

    private companion object {
        const val MSG = "confusing.assertion"

        private val ASSERT_CALLS = twoWayMapOf("assertTrue" to "assertFalse")
    }
}
