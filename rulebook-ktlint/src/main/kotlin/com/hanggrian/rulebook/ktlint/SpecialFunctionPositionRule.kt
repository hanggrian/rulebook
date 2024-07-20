package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OVERRIDE_KEYWORD
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#special-function-position)
 */
public class SpecialFunctionPositionRule : Rule("special-function-position") {
    override val tokens: TokenSet = TokenSet.create(FUN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target special function
        val identifier =
            node
                .takeIf { it.isSpecialFunction() }
                ?.findChildByType(IDENTIFIER)
                ?: return

        // checks for violation
        node
            .siblings()
            .takeIf { nodes ->
                // in Kotlin, static members belong in companion object
                nodes.any {
                    it.elementType == FUN &&
                        !it.isSpecialFunction()
                }
            } ?: return
        emit(node.startOffset, Messages.get(MSG, identifier.text), false)
    }

    internal companion object {
        const val MSG = "special.function.position"

        private val SPECIAL_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
            )

        private fun ASTNode.isSpecialFunction() =
            hasModifier(OVERRIDE_KEYWORD) &&
                findChildByType(IDENTIFIER)?.text in SPECIAL_FUNCTIONS
    }
}
