package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.AbstractCheck
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETER
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETERS

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameGenerics).
 */
class RenameGenericsCheck : AbstractCheck() {
    private companion object {
        const val MSG = "rename.generics"

        val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")
    }

    override fun getDefaultTokens(): IntArray = requiredTokens
    override fun getAcceptableTokens(): IntArray = requiredTokens
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
            log(node, Messages[MSG], false)
        }
    }
}
