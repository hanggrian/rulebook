package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.lastIf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-flattening)
 */
public class IfFlatteningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val `if` = node.lastIf ?: return
        `if`
            .takeIf { LITERAL_ELSE !in it }
            ?.findFirstToken(SLIST)
            ?.children()
            ?.filter { it.type != RCURLY && it.type != SEMI }
            ?.takeIf { it.count() > 1 }
            ?: return
        log(`if`, Messages[MSG])
    }

    internal companion object {
        const val MSG = "if.flattening"
    }
}
