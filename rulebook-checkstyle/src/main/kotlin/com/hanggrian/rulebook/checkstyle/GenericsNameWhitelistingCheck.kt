package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.contains
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
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#generics-name-whitelisting)
 */
public class GenericsNameWhitelistingCheck : Check() {
    internal var names = setOf("E", "K", "N", "T", "V")

    public fun setNames(vararg names: String) {
        this.names = names.toSet()
    }

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
            METHOD_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // filter out multiple generics
        val typeParameter =
            node
                .findFirstToken(TYPE_PARAMETERS)
                ?.children
                ?.singleOrNull { it.type == TYPE_PARAMETER }
                ?: return

        // checks for violation
        val identifier =
            typeParameter
                .findFirstToken(IDENT)
                ?.takeUnless { node.hasParentWithGenerics() || it.text in names }
                ?: return
        log(identifier, Messages.get(MSG, names.joinToString()))
    }

    internal companion object {
        const val MSG = "generics.name.whitelisting"

        private fun DetailAST.hasParentWithGenerics(): Boolean {
            var next = parent
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
