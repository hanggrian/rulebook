package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#chain-call-continuation) */
public class ChainCallContinuationCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // target root multiline chain call
        node
            .takeIf { it.isMultiline() && it.parent.type != DOT }
            ?: return

        // checks for violation
        var prevDotColumn = -1
        var dot = node.findFirstToken(DOT)
        while (dot != null) {
            if (prevDotColumn > -1 && dot.columnNo != prevDotColumn) {
                log(dot, Messages[MSG])
            }
            prevDotColumn = dot.columnNo
            dot =
                dot.findFirstToken(DOT)
                    ?: dot.findFirstToken(METHOD_CALL)?.findFirstToken(DOT)
        }
    }

    internal companion object {
        const val MSG = "chain.call.continuation"
    }
}
