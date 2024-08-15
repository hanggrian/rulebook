package com.hanggrian.rulebook.checkstyle

import com.hanggrian.rulebook.checkstyle.internals.Messages
import com.hanggrian.rulebook.checkstyle.internals.children
import com.hanggrian.rulebook.checkstyle.internals.hasAnnotation
import com.hanggrian.rulebook.checkstyle.internals.hasModifier
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_STATIC
import com.puppycrawl.tools.checkstyle.api.TokenTypes.METHOD_DEF
import com.puppycrawl.tools.checkstyle.api.TokenTypes.OBJBLOCK

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#special-function-position)
 */
public class SpecialFunctionPositionCheck : Check() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun visitToken(node: DetailAST) {
        // collect functions
        // in Java, static members have specific keyword
        val functions =
            node
                .children
                .filter { it.type == METHOD_DEF && !it.hasModifier(LITERAL_STATIC) }
                .toList()

        for ((i, function) in functions.withIndex()) {
            // target special function
            val identifier =
                function
                    .takeIf { it.isSpecialFunction() }
                    ?.findFirstToken(IDENT)
                    ?: continue

            // checks for violation
            functions
                .subList(i, functions.size)
                .takeIf { nodes -> nodes.any { !it.isSpecialFunction() } }
                ?: continue
            log(function, Messages.get(MSG, identifier.text))
        }
    }

    internal companion object {
        const val MSG = "special.function.position"

        private val SPECIAL_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        private fun DetailAST.isSpecialFunction() =
            hasAnnotation("Override") &&
                findFirstToken(IDENT)?.text in SPECIAL_FUNCTIONS
    }
}
