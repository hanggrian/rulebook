package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.maxLineNo
import com.hanggrian.rulebook.checkstyle.minLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

/** [See detail](https://hanggrian.github.io/rulebook/rules/#braces-trim) */
public class BracesTrimCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK, SLIST)

    override fun isCommentNodesRequired(): Boolean = true

    override fun visitToken(node: DetailAST) {
        // right brace is persistent
        val rcurly =
            node
                .lastChild
                .takeIf { it.type == RCURLY }
                ?: return
        val rcurlySibling =
            rcurly
                .previousSibling
                ?: return

        // left brace is conditional
        val (lcurly, lcurlySibling) =
            when (node.type) {
                OBJBLOCK -> {
                    val lcurly =
                        node
                            .firstChild
                            .takeIf { it.type == LCURLY }
                            ?: return
                    lcurly to (lcurly.nextSibling ?: return)
                }

                else -> node to node.firstChild
            }

        // checks for violation
        lcurlySibling
            .minLineNo
            .takeIf { it - lcurly.lineNo > 1 }
            ?.let { log(it - 1, Messages[MSG_FIRST]) }
        rcurlySibling
            .maxLineNo
            .takeIf { rcurly.lineNo - it > 1 }
            ?.let { log(it + 1, Messages[MSG_LAST]) }
    }

    private companion object {
        const val MSG_FIRST = "braces.trim.first"
        const val MSG_LAST = "braces.trim.last"
    }
}
