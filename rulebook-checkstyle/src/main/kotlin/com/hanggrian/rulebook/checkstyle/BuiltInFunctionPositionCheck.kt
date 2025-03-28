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

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#built-in-function-position) */
public class BuiltInFunctionPositionCheck : RulebookAstCheck() {
    override fun getRequiredTokens(): IntArray = intArrayOf(OBJBLOCK)

    override fun visitToken(node: DetailAST) {
        // collect functions
        // in Java, static members have specific keyword
        val methods =
            node
                .children
                .filter { it.type == METHOD_DEF && !it.hasModifier(LITERAL_STATIC) }
                .toList()

        for ((i, method) in methods.withIndex()) {
            // target special function
            val identifier =
                method
                    .takeIf { it.isBuiltinFunction() }
                    ?.findFirstToken(IDENT)
                    ?: continue

            // checks for violation
            methods
                .subList(i, methods.size)
                .takeIf { nodes -> nodes.any { !it.isBuiltinFunction() } }
                ?: continue
            log(method, Messages.get(MSG, identifier.text))
        }
    }

    internal companion object {
        const val MSG = "built.in.function.position"

        private val BUILTIN_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        private fun DetailAST.isBuiltinFunction() =
            hasAnnotation("Override") &&
                findFirstToken(IDENT)?.text in BUILTIN_FUNCTIONS
    }
}
