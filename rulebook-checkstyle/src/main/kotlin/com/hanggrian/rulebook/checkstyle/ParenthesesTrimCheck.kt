package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.hanggrian.rulebook.checkstyle.internals.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LPAREN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#parentheses-trim) */
public class ParenthesesTrimCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_DEF, METHOD_CALL)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // right parenthesis is persistent
        val rparen =
            node
                .findFirstToken(RPAREN)
                ?: return
        val rparenSibling =
            rparen
                .previousSibling
                ?: return

        // left parenthesis is conditional
        val (lparen, lparenSibling) =
            when (node.type) {
                METHOD_DEF -> {
                    val lparen =
                        node
                            .findFirstToken(LPAREN)
                            ?: return
                    lparen to (lparen.nextSibling ?: return)
                }

                else -> node to node.findFirstToken(ELIST)
            }

        // checks for violation
        val lparenSiblingLineNo = lparenSibling.minLineNo
        val rparenSiblingLineNo = rparenSibling.maxLineNo
        if (lparenSiblingLineNo - lparen.lineNo > 1) {
            log(lparenSiblingLineNo - 1, Messages[MSG_FIRST])
        }
        if (rparen.lineNo - rparenSiblingLineNo > 1) {
            log(rparenSiblingLineNo + 1, Messages[MSG_LAST])
        }
    }

    private companion object {
        const val MSG_FIRST = "parentheses.trim.first"
        const val MSG_LAST = "parentheses.trim.last"
    }
}
