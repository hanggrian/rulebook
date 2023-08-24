package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETER_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/UseAbstractCollection).
 */
class UseAbstractCollectionCheck : AbstractCheck() {
    private companion object {
        const val MSG_LIST = "use.abstract.collection.list"
        const val MSG_SET = "use.abstract.collection.set"
        const val MSG_MAP = "use.abstract.collection.map"
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(PARAMETER_DEF)

    override fun visitToken(node: DetailAST) {
        // only take type reference
        val ident = node.getOrNull(TYPE)?.getOrNull(IDENT) ?: return

        // report if explicit collection is found
        when (ident.text) {
            "ArrayList" -> log(ident, Messages.get(MSG_LIST, ident.text), false)
            "HashSet", "TreeSet" -> log(ident, Messages.get(MSG_SET, ident.text), false)
            "HashMap", "TreeMap" -> log(ident, Messages.get(MSG_MAP, ident.text), false)
        }
    }
}
