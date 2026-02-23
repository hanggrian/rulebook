package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-braces) */
public class UnnecessaryBracesCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_ELSE)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node
            .findFirstToken(SLIST)
            ?.children()
            ?.singleOrNull { it.type != RCURLY }
            ?.takeIf { it.type == LITERAL_IF }
            ?: return
        log(node, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.braces"
    }
}
