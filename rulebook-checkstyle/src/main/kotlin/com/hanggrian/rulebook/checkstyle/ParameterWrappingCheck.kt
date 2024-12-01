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
        val (leftParenthesis, rightParenthesis) =
            when (node.type) {
                PARAMETERS -> {
                    val lparen = node.previousSibling?.takeIf { it.type == LPAREN } ?: return
                    val rparen = node.nextSibling?.takeIf { it.type == RPAREN } ?: return
                    lparen to rparen
                }

                else -> {
                    val rparen = node.nextSibling?.takeIf { it.type == RPAREN } ?: return
                    node.parent to rparen
                }
            }

        // target multiline parameters
        val parameters =
            node
                .takeIf { rightParenthesis.lineNo > leftParenthesis.lineNo }
                ?.children
                ?.filterNot { it.type == COMMA }
                ?.toList()
                ?.takeUnless { it.isEmpty() }
                ?: return

        // checks for violation
        if (leftParenthesis.lineNo != parameters.first().minLineNo - 1) {
            log(leftParenthesis, Messages[MSG_PARENTHESIS])
        }
        if (rightParenthesis.lineNo != parameters.last().maxLineNo + 1) {
            log(rightParenthesis, Messages[MSG_PARENTHESIS])
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
