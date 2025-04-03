package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-final-name) */
public class IllegalClassFinalNameCheck : RulebookAstCheck() {
    internal var names = setOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public fun setNames(vararg names: String) {
        this.names = names.toSet()
    }

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ENUM_DEF,
            ANNOTATION_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident = node.findFirstToken(IDENT) ?: return
        val finalName =
            names
                .singleOrNull { ident.text.endsWith(it) }
                ?: return
        if (finalName in UTILITY_FINAL_NAMES) {
            log(
                ident,
                Messages.get(MSG_UTIL, ident.text.substringBefore(finalName) + 's'),
            )
            return
        }
        log(ident, Messages.get(MSG_ALL, finalName))
    }

    private companion object {
        const val MSG_ALL = "illegal.class.final.name.all"
        const val MSG_UTIL = "illegal.class.final.name.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }
}
