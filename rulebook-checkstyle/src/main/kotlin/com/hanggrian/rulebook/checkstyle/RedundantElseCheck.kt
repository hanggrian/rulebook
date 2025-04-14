package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.hasJumpStatement
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-else) */
public class RedundantElseCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_IF)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // skip single if
        node
            .takeIf { LITERAL_ELSE in it }
            ?: return

        // checks for violation
        var `if`: DetailAST? = node
        while (`if` != null) {
            `if`
                .takeIf { it.hasJumpStatement() }
                ?: return
            val lastElse = `if`.findFirstToken(LITERAL_ELSE)
            if (lastElse != null) {
                log(lastElse, Messages[MSG])
            }
            `if` = lastElse?.findFirstToken(LITERAL_IF)
        }
    }

    private companion object {
        const val MSG = "redundant.else"
    }
}
