package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.isStatement
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-if) */
public class LonelyIfCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_ELSE)

    override fun visit(node: DetailAST) {
        // checks for violation
        val `if` =
            node
                .findFirstToken(SLIST)
                ?.children()
                ?.singleOrNull { it.isStatement() }
                ?.takeIf { it.type == LITERAL_IF }
                ?: return
        log(`if`, Messages[MSG])
    }

    private companion object {
        const val MSG = "lonely.if"
    }
}
