package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-parentheses-in-lambda) */
public class UnnecessaryParenthesesInLambdaCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LAMBDA)

    override fun visitToken(node: DetailAST) {
        // skip multiple parameters
        val parameter =
            node
                .findFirstToken(PARAMETERS)
                ?.children()
                ?.singleOrNull()
                ?: return

        // checks for violation
        parameter
            .takeIf { LPAREN in node && RPAREN in node }
            ?.findFirstToken(TYPE)
            ?.takeUnless { IDENT in it }
            ?: return
        log(parameter, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.parentheses.in.lambda"
    }
}
