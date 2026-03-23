package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.isStatement
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SEMI
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-return) */
public class UnnecessaryReturnCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_DEF)

    override fun visit(node: DetailAST) {
        // checks for violation
        val `return` =
            node
                .findFirstToken(SLIST)
                ?.children()
                ?.lastOrNull { it.isStatement() }
                ?.takeIf {
                    it.type == LITERAL_RETURN &&
                        it.children().singleOrNull()?.type == SEMI
                } ?: return
        log(`return`, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.return"
    }
}
