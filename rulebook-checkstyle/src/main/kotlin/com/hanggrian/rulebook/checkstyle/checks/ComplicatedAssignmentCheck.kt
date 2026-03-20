package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ASSIGN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DIV
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MINUS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MOD
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PLUS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.STAR

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-assignment) */
public class ComplicatedAssignmentCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(EXPR)

    override fun visitToken(node: DetailAST) {
        // skip shorthand operator
        val assign = node.findFirstToken(ASSIGN) ?: return

        // checks for violation
        val ident = assign.findFirstToken(IDENT) ?: return
        val operation =
            (
                assign.findFirstToken(PLUS)
                    ?: assign.findFirstToken(MINUS)
                    ?: assign.findFirstToken(STAR)
                    ?: assign.findFirstToken(DIV)
                    ?: assign.findFirstToken(MOD)
            )?.deepestOperation
                ?: return
        operation
            .findFirstToken(IDENT)
            ?.takeIf { it.text == ident.text }
            ?: return
        log(assign, Messages[MSG, operation.text + "="])
    }

    private companion object {
        const val MSG = "complicated.assignment"

        val DetailAST.deepestOperation: DetailAST
            get() {
                var current: DetailAST = this
                while (PLUS in current ||
                    MINUS in current ||
                    STAR in current ||
                    DIV in current ||
                    MOD in current
                ) {
                    current =
                        current.findFirstToken(PLUS)
                            ?: current.findFirstToken(MINUS)
                            ?: current.findFirstToken(STAR)
                            ?: current.findFirstToken(DIV)
                            ?: current.findFirstToken(MOD)!!
                }
                return current
            }
    }
}
