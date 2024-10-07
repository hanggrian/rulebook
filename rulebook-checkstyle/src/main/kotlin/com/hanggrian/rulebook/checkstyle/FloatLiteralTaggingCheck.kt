package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_FLOAT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.NUM_INT

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#float-literal-tagging)
 */
public class FloatLiteralTaggingCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(NUM_FLOAT, NUM_INT)

    override fun visitToken(node: DetailAST) {
        // find trailing tag
        val character =
            node.text
                .last()
                .takeIf { it.lowercaseChar() == 'f' }
                ?: return

        // checks for violation
        if (node.type == NUM_INT) {
            character
                .takeIf { c -> c.isLowerCase() }
                ?: return
            log(node, Messages[MSG_HEX])
        }
        character
            .takeIf { it.isUpperCase() }
            ?: return
        log(node, Messages[MSG_NUM])
    }

    internal companion object {
        const val MSG_NUM = "float.literal.tagging.num"
        const val MSG_HEX = "float.literal.tagging.hex"
    }
}
