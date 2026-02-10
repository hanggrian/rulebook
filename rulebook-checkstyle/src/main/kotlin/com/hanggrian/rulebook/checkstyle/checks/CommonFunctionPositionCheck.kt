package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.hanggrian.rulebook.checkstyle.children
import com.hanggrian.rulebook.checkstyle.hasAnnotation
import com.hanggrian.rulebook.checkstyle.hasModifier
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_STATIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK

/** [See detail](https://hanggrian.github.io/rulebook/rules/#common-function-position) */
public class CommonFunctionPositionCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun visitToken(node: DetailAST) {
        // collect functions
        // in Java, static members have specific keyword
        val methods =
            node
                .children()
                .filter { it.type == METHOD_DEF && !it.hasModifier(LITERAL_STATIC) }
                .toList()

        for ((i, method) in methods.withIndex()) {
            // target special function
            val identifier =
                method
                    .takeIf { it.isBuiltInFunction() }
                    ?.findFirstToken(IDENT)
                    ?: continue

            // checks for violation
            methods
                .subList(i, methods.size)
                .takeIf { nodes -> nodes.any { !it.isBuiltInFunction() } }
                ?: continue
            log(method, Messages[MSG, identifier.text])
        }
    }

    private companion object {
        const val MSG = "common.function.position"

        private val COMMON_FUNCTIONS =
            hashSetOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        fun DetailAST.isBuiltInFunction() =
            hasAnnotation("Override") &&
                findFirstToken(IDENT)?.text in COMMON_FUNCTIONS
    }
}
