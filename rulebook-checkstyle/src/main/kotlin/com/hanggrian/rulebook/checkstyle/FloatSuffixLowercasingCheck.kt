package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_FLOAT

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#float-suffix-lowercasing) */
public class FloatSuffixLowercasingCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(NUM_FLOAT)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        node
            .text
            .last()
            .takeIf { it == 'F' }
            ?: return
        log(node, Messages[MSG])
    }

    internal companion object {
        const val MSG = "float.suffix.lowercasing"
    }
}
