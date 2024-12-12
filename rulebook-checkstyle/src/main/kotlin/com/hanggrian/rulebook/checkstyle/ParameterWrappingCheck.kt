package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMA
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#parameter-wrapping) */
public class ParameterWrappingCheck : RulebookCheck() {
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
        if (lparen.lineNo != parameters.first().minLineNo - 1) {
            log(lparen, Messages[MSG_PARENTHESIS])
        }
        if (rparen.lineNo != parameters.last().maxLineNo + 1) {
            log(rparen, Messages[MSG_PARENTHESIS])
        }
        for ((i, parameter) in parameters.withIndex().drop(1)) {
            parameters[i - 1]
                .takeUnless { it.maxLineNo + 1 == parameter.minLineNo }
                ?: continue
            log(parameter, Messages[MSG_ARGUMENT])
        }
    }

    internal companion object {
        const val MSG_PARENTHESIS = "parameter.wrapping.parenthesis"
        const val MSG_ARGUMENT = "parameter.wrapping.argument"
    }
}
