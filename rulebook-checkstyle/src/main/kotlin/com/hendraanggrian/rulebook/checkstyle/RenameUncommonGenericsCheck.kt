package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.RulebookCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETER
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETERS

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameUncommonGenerics).
 */
class RenameUncommonGenericsCheck : RulebookCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(TYPE_PARAMETERS)

    override fun visitToken(node: DetailAST) {
        // filter out multiple generics
        val typeParameterList = mutableListOf<DetailAST>()
        var next = node.firstChild
        while (next != null) {
            if (next.type == TYPE_PARAMETER) {
                typeParameterList += next
            }
            next = next.nextSibling
        }
        val typeParameter = typeParameterList.singleOrNull { it.type == TYPE_PARAMETER } ?: return

        // check for a match
        val ident = typeParameter.findFirstToken(IDENT) ?: return
        if (ident.text !in COMMON_GENERICS) {
            log(node, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "rename.uncommon.generics"

        private val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")
    }
}
