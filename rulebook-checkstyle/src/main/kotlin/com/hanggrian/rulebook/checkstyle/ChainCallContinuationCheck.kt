package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.lastMostChild
import com.hanggrian.rulebook.checkstyle.internals.maxLineNo
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#chain-call-continuation) */
public class ChainCallContinuationCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // target root multiline chain call
        node
            .takeIf { it.isMultiline() && it.parent.type != DOT }
            ?: return

        // checks for violation
        var dot = node.findFirstToken(DOT)
        while (dot != null) {
            val dotSibling = dot.firstChild.lastMostChild
            if ((dotSibling.type == RPAREN || dotSibling.type == RCURLY) &&
                dotSibling.lineNo != dotSibling.previousSibling?.maxLineNo
            ) {
                if (dot.lineNo != dotSibling.lineNo) {
                    log(dot, Messages[MSG_UNEXPECTED])
                }
            } else {
                if (dot.lineNo != dotSibling.lineNo + 1) {
                    log(dot, Messages[MSG_MISSING])
                }
            }
            dot =
                dot.findFirstToken(DOT)
                    ?: dot.findFirstToken(METHOD_CALL)?.findFirstToken(DOT)
        }
    }

    internal companion object {
        const val MSG_MISSING = "chain.call.continuation.missing"
        const val MSG_UNEXPECTED = "chain.call.continuation.unexpected"
    }
}
