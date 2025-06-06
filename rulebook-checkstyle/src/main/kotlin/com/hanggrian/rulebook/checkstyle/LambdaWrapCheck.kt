package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.firstLeaf
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lambda-wrap) */
public class LambdaWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LAMBDA)

    override fun visitToken(node: DetailAST) {
        // target multiline lambda
        val expr =
            (node.findFirstToken(EXPR) ?: node.findFirstToken(SLIST).findFirstToken(EXPR))
                ?.takeIf { it.isMultiline() }
                ?: return
        val parameters =
            node.findFirstToken(IDENT)
                ?: node.findFirstToken(PARAMETERS)
                ?: return

        // checks for violation
        expr
            .minLineNo
            .takeIf { it == parameters.maxLineNo }
            ?: return
        log(expr.firstLeaf(), Messages[MSG])
    }

    private companion object {
        const val MSG = "lambda.wrap"
    }
}
