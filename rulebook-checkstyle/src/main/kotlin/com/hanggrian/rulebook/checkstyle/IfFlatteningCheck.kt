package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.joinText
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-flattening)
 */
public class IfFlatteningCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(SLIST)

    override fun visitToken(node: DetailAST) {
        // only proceed on one if and no else
        val children = node.children().toList()
        val if2 =
            children
                .takeIf { it.lastOrNull()?.type == RCURLY }
                ?.dropLast(1)
                ?.singleOrNull()
                ?.takeIf { it.type == LITERAL_IF }
                ?.takeUnless { LITERAL_ELSE in it }
                ?: return

        println(if2.joinText())

        // checks for violation
        if2
            .findFirstToken(SLIST)
            ?.children()
            ?.toList()
            ?.takeIf { it.size > 2 }
            ?: return
        log(if2, Messages[MSG])
    }

    internal companion object {
        const val MSG = "if.flattening"
    }
}
