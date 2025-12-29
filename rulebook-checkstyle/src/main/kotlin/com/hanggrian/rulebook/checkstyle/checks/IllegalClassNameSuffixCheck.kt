package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ENUM_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.RECORD_DEF

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-name-suffix) */
public class IllegalClassNameSuffixCheck : RulebookAstCheck() {
    public var names: String = "Util, Utility, Helper, Manager, Wrapper"

    internal val nameList get() = names.split(',').map { it.trim() }

    override fun getRequiredTokens(): IntArray =
        intArrayOf(
            CLASS_DEF,
            INTERFACE_DEF,
            ANNOTATION_DEF,
            ENUM_DEF,
            RECORD_DEF,
        )

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident = node.findFirstToken(IDENT) ?: return
        val finalName =
            nameList
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
        const val MSG_ALL = "illegal.class.name.suffix.all"
        const val MSG_UTIL = "illegal.class.name.suffix.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }
}
