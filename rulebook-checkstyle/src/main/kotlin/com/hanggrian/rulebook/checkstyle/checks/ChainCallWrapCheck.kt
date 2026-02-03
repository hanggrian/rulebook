package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.lastLeaf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#chain-call-wrap) */
public class ChainCallWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // target root chain call
        node
            .takeUnless { it.parent.type == DOT }
            ?: return

        // collect dots
        val dots =
            generateSequence(node.findFirstToken(DOT)) {
                it.findFirstToken(DOT)
                    ?: it.findFirstToken(METHOD_CALL)?.findFirstToken(DOT)
            }

        // skip dots in single-line
        val firstDot = dots.firstOrNull() ?: return
        if (dots.all { it.lineNo == firstDot.lineNo }) {
            return
        }

        // checks for violation
        for (dot in dots) {
            val dotSibling = dot.firstChild?.lastLeaf() ?: continue
            if ((dotSibling.type == RPAREN || dotSibling.type == RCURLY) &&
                dotSibling.lineNo != dotSibling.previousSibling?.lineNo
            ) {
                if (dot.lineNo != dotSibling.lineNo) {
                    log(dot, Messages[MSG_UNEXPECTED])
                }
                continue
            }
            if (dot.lineNo != dotSibling.lineNo + 1) {
                log(dot, Messages[MSG_MISSING])
            }
        }
    }

    private companion object {
        const val MSG_MISSING = "chain.call.wrap.missing"
        const val MSG_UNEXPECTED = "chain.call.wrap.unexpected"
    }
}
