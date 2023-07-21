package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NEW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#throw-exception-ambiguity).
 */
class ThrowExceptionAmbiguityCheck : AbstractCheck() {
    private companion object {
        const val ERROR_MESSAGE = "Ambiguous exception '%s'."

        private val IDENT_TARGETS = setOf("Exception", "Error", "Throwable")
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_THROW)

    override fun visitToken(node: DetailAST) {
        // only target declaration, reference such as `Exception error = new Exception()` is ignored
        val literalNew = node[EXPR].findFirstToken(LITERAL_NEW) ?: return

        // only target supertype
        val ident = literalNew[IDENT]
        if (ident.text !in IDENT_TARGETS) {
            return
        }

        // report error if there is no message
        if (EXPR !in literalNew[ELIST]) {
            log(ident, ERROR_MESSAGE.format(ident.text))
        }
    }
}
