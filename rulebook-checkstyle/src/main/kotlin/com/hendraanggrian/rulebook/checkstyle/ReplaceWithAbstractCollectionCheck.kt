package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.PARAMETER_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ReplaceWithAbstractCollection).
 */
class ReplaceWithAbstractCollectionCheck : AbstractCheck() {
    private companion object {
        const val MSG_LIST = "replace.with.abstract.collection.list"
        const val MSG_SET = "replace.with.abstract.collection.set"
        const val MSG_MAP = "replace.with.abstract.collection.map"
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
    override fun getRequiredTokens(): IntArray = intArrayOf(PARAMETER_DEF)

    override fun visitToken(node: DetailAST) {
        // only take type reference
        val ident = node.findFirstToken(TYPE)?.findFirstToken(IDENT) ?: return

        // report if explicit collection is found
        when (ident.text) {
            "ArrayList" -> log(ident, Messages[MSG_LIST], false)
            "HashSet", "TreeSet" -> log(ident, Messages[MSG_SET], false)
            "HashMap", "TreeMap" -> log(ident, Messages[MSG_MAP], false)
        }
    }
}
