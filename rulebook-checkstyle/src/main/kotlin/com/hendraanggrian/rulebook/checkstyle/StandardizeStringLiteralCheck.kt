package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.STRING_LITERAL

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/UncommonStringLiteral).
 */
class StandardizeStringLiteralCheck : AbstractCheck() {
    private companion object {
        const val MSG_ENCODING = "standardize.string.literal.encoding"
        const val MSG_COLOR = "standardize.string.literal.color"

        val PROHIBITED_ENCODINGS = setOf("\"utf-8\"", "\"utf-16\"", "\"utf-32\"", "\"ascii\"")
        val COLOR_REGEX = "^\"#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\"$"
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(STRING_LITERAL)

    private var colorRegex: Regex? = null

    override fun visitToken(node: DetailAST) {
        // initialize regex
        if (colorRegex == null) {
            colorRegex = Regex(COLOR_REGEX)
        }

        val literal = node.text
        when {
            // encoding must be all uppercase
            literal in PROHIBITED_ENCODINGS ->
                log(node, Messages.get(MSG_ENCODING, literal.trimQuotes().uppercase()), false)
            // color must be all lowercase
            colorRegex!!.matches(literal) -> {
                // skip '"#'
                if (literal.drop(2).any { it.isUpperCase() }) {
                    log(node, Messages.get(MSG_COLOR, literal.trimQuotes().lowercase()), false)
                }
            }
        }
    }

    private fun String.trimQuotes(): String = substring(1, lastIndex)
}
