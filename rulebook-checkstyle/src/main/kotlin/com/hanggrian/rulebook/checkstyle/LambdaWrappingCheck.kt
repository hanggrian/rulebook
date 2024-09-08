package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#lambda-wrapping)
 */
public class LambdaWrappingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LAMBDA)

    override fun visitToken(node: DetailAST) {
        // target multiline lambda
        val expression =
            node
                .takeIf { it.isMultiline() }
                ?.let {
                    it.findFirstToken(EXPR)
                        ?: it.findFirstToken(SLIST).findFirstToken(EXPR)
                } ?: return
        val parameters =
            node.findFirstToken(IDENT)
                ?: node.findFirstToken(PARAMETERS)
                ?: return

        // checks for violation
        expression
            .takeUnless { it.minLineNo == parameters.maxLineNo + 1 }
            ?: return
        log(expression, Messages[MSG])
    }

    internal companion object {
        const val MSG = "lambda.wrapping"
    }
}
