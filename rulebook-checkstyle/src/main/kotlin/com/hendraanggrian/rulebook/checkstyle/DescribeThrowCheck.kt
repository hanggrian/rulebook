package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ELIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXPR
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_NEW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/DescribeThrow).
 */
class DescribeThrowCheck : AbstractCheck() {
    private companion object {
        const val MSG = "describe.throw"

        val AMBIGUOUS_ERRORS = setOf("Exception", "Error", "Throwable")
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(LITERAL_THROW)

    override fun visitToken(node: DetailAST) {
        // only target declaration, reference such as `Exception error = new Exception()` is ignored
        val literalNew = node.getOrNull(EXPR)?.getOrNull(LITERAL_NEW) ?: return

        // only target supertype
        val ident = literalNew.getOrNull(IDENT) ?: return
        if (ident.text !in AMBIGUOUS_ERRORS) {
            return
        }

        // report error if there is no message
        val elist = literalNew.findFirstToken(ELIST) ?: return
        if (EXPR !in elist) {
            log(ident, Messages.get(MSG, ident.text))
        }
    }
}
