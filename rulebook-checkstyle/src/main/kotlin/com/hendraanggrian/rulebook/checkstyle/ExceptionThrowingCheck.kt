package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NEW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#exception-throwing).
 */
public class ExceptionThrowingCheck : RulebookCheck() {
    public override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_THROW)

    public override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident =
            node.findFirstToken(EXPR)
                ?.findFirstToken(LITERAL_NEW)
                ?.findFirstToken(IDENT)
                ?.takeIf { it.text in AMBIGUOUS_ERRORS }
                ?: return
        log(ident, Messages[MSG])
    }

    internal companion object {
        const val MSG = "exception.throwing"

        private val AMBIGUOUS_ERRORS = setOf("Exception", "Error", "Throwable")
    }
}
