package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT

/** [See detail](https://hanggrian.github.io/rulebook/rules/#exception-inheritance) */
public class ExceptionInheritanceCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(CLASS_DEF)

    override fun visitToken(node: DetailAST) {
        // checks for violation
        val ident =
            node
                .findFirstToken(EXTENDS_CLAUSE)
                ?.findFirstToken(IDENT)
                ?.takeIf { it.text in NON_APPLICATION_EXCEPTIONS }
                ?: return
        log(ident, Messages[MSG])
    }

    internal companion object {
        const val MSG = "exception.inheritance"

        private val NON_APPLICATION_EXCEPTIONS =
            setOf(
                "Error",
                "Throwable",
                "java.lang.Error",
                "java.lang.Throwable",
            )
    }
}
