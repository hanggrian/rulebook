package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.contains
import com.hanggrian.rulebook.checkstyle.hasJumpStatement
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
            val `else` =
                `if`
                    .findFirstToken(LITERAL_ELSE)
                    ?: return
            log(`else`, Messages[MSG])
            `if` = `else`.findFirstToken(LITERAL_IF)
        }
    }

    private companion object {
        const val MSG = "redundant.else"
    }
}
