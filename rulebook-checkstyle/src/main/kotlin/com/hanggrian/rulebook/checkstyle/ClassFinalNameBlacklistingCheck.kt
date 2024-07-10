package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-final-name-blacklisting)
 */
public class ClassFinalNameBlacklistingCheck : Check() {
    private var names = setOf("Util", "Utility", "Helper", "Manager", "Wrapper")

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
        val finalName = names.firstOrNull { ident.text.endsWith(it) } ?: return
        when (finalName) {
            "Util", "Utility" ->
                log(ident, Messages.get(MSG_UTIL, ident.text.substringBefore(finalName) + 's'))
            else -> log(ident, Messages.get(MSG_ALL, finalName))
        }
    }

    internal companion object {
        const val MSG_ALL = "class.final.name.blacklisting.all"
        const val MSG_UTIL = "class.final.name.blacklisting.util"
    }
}
