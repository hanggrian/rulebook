package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NEW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#throw-exception-subclass).
 */
class ThrowExceptionSubclassCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_THROW)

    override fun visitToken(node: DetailAST) {
        // only target declaration, reference such as `Exception error = new Exception()` is ignored
        val literalNew = node.findFirstToken(EXPR)?.findFirstToken(LITERAL_NEW) ?: return

        // only target supertype
        val ident = literalNew.findFirstToken(IDENT) ?: return

        // report error if superclass exception is found
        if (ident.text in AMBIGUOUS_ERRORS) {
            log(ident, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "throw.exception.subclass"

        private val AMBIGUOUS_ERRORS = setOf("Exception", "Error", "Throwable")
    }
}
