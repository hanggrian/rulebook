package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
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
        // checks for violation
        val parameter =
            node
                .findFirstToken(PARAMETERS)
                ?.children
                ?.singleOrNull()
                ?.takeIf {
                    (IDENT !in it.findFirstToken(TYPE)) &&
                        LPAREN in node &&
                        RPAREN in node
                } ?: return
        log(parameter, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.parentheses.in.lambda"
    }
}
