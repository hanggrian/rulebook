package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_INT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-hex) */
public class LowercaseHexCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(NUM_INT)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val value =
            node
                .text
                .takeIf { it.startsWith("0x", true) }
                ?: return
        val valueReplacement =
            value
                .lowercase()
                .takeUnless { it == value }
                ?: return
        log(node, Messages[MSG, valueReplacement])
    }

    private companion object {
        const val MSG = "lowercase.hex"
    }
}
