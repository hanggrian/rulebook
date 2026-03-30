package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EQUAL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_FALSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NULL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_TRUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NOT_EQUAL

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-assertion) */
public class ComplicatedAssertionCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun isTest(): Boolean = true

    override fun visit(node: DetailAST) {
        // checks for violation
        val identifier =
            node
                .findFirstToken(IDENT)
                ?: return
        val elist = node.findFirstToken(ELIST) ?: return
        val callReplacement =
            when (identifier.text) {
                "assertTrue", "assertFalse" -> {
                    val expr =
                        elist
                            .findFirstToken(EXPR)
                            ?: return
                    when (expr.firstChild.type) {
                        EQUAL -> "assertEquals"
                        NOT_EQUAL -> "assertNotEquals"
                        else -> return
                    }
                }

                "assertEquals", "assertNotEquals" -> {
                    val exprs =
                        elist
                            .children()
                            .filter { it.type == EXPR }
                    when {
                        exprs.any { LITERAL_TRUE in it } -> "assertTrue"
                        exprs.any { LITERAL_FALSE in it } -> "assertFalse"
                        exprs.any { LITERAL_NULL in it } -> "assertNull"
                        else -> return
                    }
                }

                else -> return
            }
        log(node, Messages[MSG, callReplacement])
    }

    private companion object {
        const val MSG = "complicated.assertion"
    }
}
