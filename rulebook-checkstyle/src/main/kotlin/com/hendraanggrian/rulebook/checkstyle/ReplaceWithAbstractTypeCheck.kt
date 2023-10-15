package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETER_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ReplaceWithAbstractType).
 */
class ReplaceWithAbstractTypeCheck : AbstractCheck() {
    private companion object {
        const val MSG = "replace.with.abstract.type"
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(PARAMETER_DEF)

    override fun visitToken(node: DetailAST) {
        // only take type reference
        val ident = node.findFirstToken(TYPE)?.findFirstToken(IDENT) ?: return

        // report if explicit collection is found
        when (ident.text) {
            "ArrayList" -> log(ident, Messages.get(MSG, "List"), false)
            "HashSet", "TreeSet" -> log(ident, Messages.get(MSG, "Set"), false)
            "HashMap", "TreeMap" -> log(ident, Messages.get(MSG, "Map"), false)
            "FileInputStream", "ByteArrayInputStream" -> log(
                ident,
                Messages.get(MSG, "InputStream"),
                false
            )
            "FileOutputStream", "ByteArrayOutputStream" -> log(
                ident,
                Messages.get(MSG, "OutputStream"),
                false
            )
        }
    }
}
