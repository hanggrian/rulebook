package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.isComment
import com.hanggrian.rulebook.checkstyle.nextSibling
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CONTINUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_DO
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_FOR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_WHILE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-continue) */
public class UnnecessaryContinueCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_FOR, LITERAL_WHILE, LITERAL_DO)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        var `continue` =
            when (node.type) {
                LITERAL_DO -> node.firstChild

                else ->
                    node
                        .firstChild
                        .nextSibling { it.type == RPAREN }
                        ?.nextSibling
            } ?: return
        `continue` =
            `continue`
                .takeIf { it.type == SLIST }
                ?.let { n ->
                    n.children().lastOrNull {
                        it.type != RCURLY &&
                            !it.isComment()
                    }
                } ?: `continue`
        `continue`
            .takeIf { it.type == LITERAL_CONTINUE }
            ?: return
        log(`continue`, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.continue"
    }
}
