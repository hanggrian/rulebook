package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-exception-extending) */
public class ClassExceptionExtendingCheck : RulebookCheck() {
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
        const val MSG = "class.exception.extending"

        private val NON_APPLICATION_EXCEPTIONS =
            setOf(
                "Error",
                "Throwable",
                "java.lang.Error",
                "java.lang.Throwable",
            )
    }
}
