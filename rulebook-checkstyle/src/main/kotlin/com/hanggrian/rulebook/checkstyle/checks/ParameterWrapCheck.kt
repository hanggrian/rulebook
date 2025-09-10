package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.maxLineNo
import com.hanggrian.rulebook.checkstyle.minLineNo
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
                ?.children()
                ?.filterNot { it.type == COMMA }
                ?.toList()
                ?.takeUnless { it.isEmpty() }
                ?: return

        // checks for violation
        if (parameters.size == 1 && parameters.single().type == LAMBDA) {
            return
        }
        for ((i, parameter) in parameters.withIndex().drop(1)) {
            parameters[i - 1]
                .maxLineNo
                .takeIf { it == parameter.minLineNo }
                ?: continue
            log(parameter, Messages[MSG])
        }
    }

    private companion object {
        const val MSG = "parameter.wrap"
    }
}
