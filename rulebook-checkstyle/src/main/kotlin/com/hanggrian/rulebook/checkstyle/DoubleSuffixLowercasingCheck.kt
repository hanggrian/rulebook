package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_DOUBLE

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#double-suffix-lowercasing) */
public class DoubleSuffixLowercasingCheck : RulebookCheck() {
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

    internal companion object {
        const val MSG = "double.suffix.lowercasing"
    }
}
