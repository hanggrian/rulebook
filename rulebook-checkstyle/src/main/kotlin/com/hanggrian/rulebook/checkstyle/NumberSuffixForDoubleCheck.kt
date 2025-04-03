package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_DOUBLE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-double) */
public class NumberSuffixForDoubleCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(NUM_DOUBLE)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node
            .text
            .last()
            .takeIf { it == 'D' }
            ?: return
        log(node, Messages[MSG])
    }

    private companion object {
        const val MSG = "number.suffix.for.double"
    }
}
