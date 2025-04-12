package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.contains
import com.hanggrian.rulebook.checkstyle.internals.isMultiline
import com.hanggrian.rulebook.checkstyle.internals.lastLeaf
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.DOT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_CALL
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RCURLY
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RPAREN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#chain-call-wrap) */
public class ChainCallWrapCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(METHOD_CALL)

    override fun visitToken(node: DetailAST) {
        // target root multiline chain call
        node
            .takeIf { it.isMultiline() && it.parent.type != DOT }
            ?: return

        // single call is by definition not chained
        var dot: DetailAST? =
            node
                .findFirstToken(DOT)
                ?.takeUnless {
                    DOT !in it &&
                        it.findFirstToken(METHOD_CALL)?.findFirstToken(DOT) == null
                } ?: return

        // checks for violation
        while (dot != null) {
            val dotSibling = dot.firstChild?.lastLeaf()
            if (dotSibling != null) {
                if ((dotSibling.type == RPAREN || dotSibling.type == RCURLY) &&
                    dotSibling.lineNo != dotSibling.previousSibling?.lineNo
                ) {
                    if (dot.lineNo != dotSibling.lineNo) {
                        log(dot, Messages[MSG_UNEXPECTED])
                    }
                } else {
                    if (dot.lineNo != dotSibling.lineNo + 1) {
                        log(dot, Messages[MSG_MISSING])
                    }
                }
            }
            dot =
                dot.findFirstToken(DOT)
                    ?: dot.findFirstToken(METHOD_CALL)?.findFirstToken(DOT)
        }
    }

    private companion object {
        const val MSG_MISSING = "chain.call.wrap.missing"
        const val MSG_UNEXPECTED = "chain.call.wrap.unexpected"
    }
}
