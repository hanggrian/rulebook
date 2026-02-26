package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.contains
import com.hanggrian.rulebook.checkstyle.isComment
import com.hanggrian.rulebook.checkstyle.nextSibling
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_ELSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_FALSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_IF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_TRUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-if) */
public class RedundantIfCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_IF)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node
            .firstChild
            .nextSibling { it.type == RPAREN }
            ?.nextSibling
            ?.takeIf {
                it.isStatementConstant() ||
                    it.isThenConstant()
            } ?: return
        node
            .findFirstToken(LITERAL_ELSE)
            ?.takeIf {
                LITERAL_IF !in it && (
                    it.children().singleOrNull()?.isStatementConstant() ?: false ||
                        it.findFirstToken(SLIST)?.isThenConstant() ?: false
                )
            }?.run {
                log(node, Messages[MSG])
                return
            }
        node
            .nextSibling {
                it.type != RCURLY &&
                    !it.isComment()
            }?.takeIf { it.isStatementConstant() }
            ?: return
        log(node, Messages[MSG])
    }

    private companion object {
        const val MSG = "redundant.if"

        fun DetailAST.isThenConstant(): Boolean =
            children()
                .singleOrNull {
                    it.type != RCURLY &&
                        !it.isComment()
                }?.isStatementConstant()
                ?: false

        fun DetailAST.isStatementConstant(): Boolean =
            when (type) {
                EXPR -> LITERAL_TRUE in this || LITERAL_FALSE in this
                LITERAL_RETURN -> firstChild.isStatementConstant()
                else -> false
            }
    }
}
