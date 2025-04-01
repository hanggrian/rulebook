package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-parentheses-in-lambda) */
public class UnnecessaryParenthesesInLambdaCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LAMBDA)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val parameter =
            node
                .findFirstToken(PARAMETERS)
                ?.children
                ?.singleOrNull()
                ?.takeIf { LPAREN in node && RPAREN in node }
                ?: return
        log(parameter, Messages[MSG])
    }

    internal companion object {
        const val MSG = "unnecessary.parentheses.in.lambda"
    }
}
