package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OVERRIDE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.psiUtil.children

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#built-in-function-position) */
public class BuiltInFunctionPositionRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // collect functions
        // in Kotlin, static members belong in companion object
        val funs =
            node
                .children()
                .filter { it.elementType == FUN }
                .toList()

        for ((i, `fun`) in funs.withIndex()) {
            // target special function
            val identifier =
                `fun`
                    .takeIf { it.isBuiltinFunction() }
                    ?.findChildByType(IDENTIFIER)
                    ?: continue

            // checks for violation
            funs
                .subList(i, funs.size)
                .takeIf { nodes -> nodes.any { !it.isBuiltinFunction() } }
                ?: continue
            emit(`fun`.startOffset, Messages.get(MSG, identifier.text), false)
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:built-in-function-position")

        const val MSG = "built.in.function.position"

        private val BUILTIN_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
            )

        private fun ASTNode.isBuiltinFunction() =
            hasModifier(OVERRIDE_KEYWORD) &&
                findChildByType(IDENTIFIER)?.text in BUILTIN_FUNCTIONS
    }
}
