package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LAMBDA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#parameter-wrap) */
public class ParameterWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(PARAMETERS, ELIST)

    override fun visitToken(node: DetailAST) {
        // collect parentheses
        val (lparen, rparen) =
            when (node.type) {
                PARAMETERS ->
                    (node.previousSibling?.takeIf { it.type == LPAREN } ?: return) to
                        (node.nextSibling?.takeIf { it.type == RPAREN } ?: return)

                else ->
                    node.parent to
                        (node.nextSibling?.takeIf { it.type == RPAREN } ?: return)
            }

        // target multiline parameters
        val parameters =
            node
                .takeIf { rparen.lineNo > lparen.lineNo }
                ?.children
                ?.filterNot { it.type == COMMA }
                ?.toList()
                ?.takeUnless { it.isEmpty() }
                ?: return

        // checks for violation
        if (parameters.size == 1 && parameters.single().type == LAMBDA) {
            return
        }
        if (lparen.lineNo != parameters.first().minLineNo - 1) {
            log(lparen, Messages[MSG_PARENTHESIS])
        }
        if (rparen.lineNo != parameters.last().maxLineNo + 1) {
            log(rparen, Messages[MSG_PARENTHESIS])
        }
        for ((i, parameter) in parameters.withIndex().drop(1)) {
            parameters[i - 1]
                .takeIf { it.maxLineNo == parameter.minLineNo }
                ?: continue
            log(parameter, Messages[MSG_ARGUMENT])
        }
    }

    private companion object {
        const val MSG_PARENTHESIS = "parameter.wrap.parenthesis"
        const val MSG_ARGUMENT = "parameter.wrap.argument"
    }
}
