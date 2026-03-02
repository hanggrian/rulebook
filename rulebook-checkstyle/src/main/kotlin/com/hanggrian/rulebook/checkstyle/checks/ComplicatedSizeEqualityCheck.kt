package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EQUAL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.GT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_INT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-size-equality) */
public class ComplicatedSizeEqualityCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(EXPR)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val operation =
            node.findFirstToken(GT)
                ?: node.findFirstToken(LT)
                ?: node.findFirstToken(EQUAL)
                ?: return
        operation
            .findFirstToken(NUM_INT)
            ?.takeIf { it.text == "0" }
            ?: return
        val call =
            operation
                .findFirstToken(METHOD_CALL)
                ?.findFirstToken(DOT)
                ?.children()
                ?.filter { it.type == IDENT }
                ?.toList()
                ?.takeIf { it.size == 2 }
                ?.get(1)
                ?.takeIf { it.text == "size" }
                ?: return
        log(
            call,
            Messages[
                MSG,
                when (operation.type) {
                    GT, LT -> "!isEmpty"
                    else -> "isEmpty"
                },
            ],
            false,
        )
    }

    private companion object {
        const val MSG = "complicated.size.equality"
    }
}
