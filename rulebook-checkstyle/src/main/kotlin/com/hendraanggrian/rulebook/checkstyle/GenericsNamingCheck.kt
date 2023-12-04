package com.hendraanggrian.rulebook.checkstyle

import com.hendraanggrian.rulebook.checkstyle.internals.Messages
import com.hendraanggrian.rulebook.checkstyle.internals.children
import com.hendraanggrian.rulebook.checkstyle.internals.contains
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETER
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE_PARAMETERS

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-naming).
 */
public class GenericsNamingCheck : RulebookCheck() {
    private var commonGenerics = setOf("E", "K", "N", "T", "V")

    public fun setCommonGenerics(vararg generics: String) {
        commonGenerics = generics.toSet()
    }

    public override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
            METHOD_DEF,
        )

    public override fun visitToken(node: DetailAST) {
        // filter out multiple generics
        val typeParameter =
            node.findFirstToken(TYPE_PARAMETERS)?.children()
                ?.singleOrNull { it.type == TYPE_PARAMETER }
                ?: return

        // checks for violation
        val ident =
            typeParameter.findFirstToken(IDENT)
                ?.takeIf { !node.hasParentWithGenerics() && it.text !in commonGenerics }
                ?: return
        log(ident, Messages.get(MSG, commonGenerics.joinToString()))
    }

    internal companion object {
        const val MSG = "generics.naming"

        private fun DetailAST.hasParentWithGenerics(): Boolean {
            var next: DetailAST? = parent
            while (next != null) {
                if (TYPE_PARAMETERS in next) {
                    return true
                }
                next = next.parent
            }
            return false
        }
    }
}
