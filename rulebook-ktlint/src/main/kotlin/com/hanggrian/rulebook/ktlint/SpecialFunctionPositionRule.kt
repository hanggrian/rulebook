package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OVERRIDE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.siblings

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#special-function-position)
 */
public class SpecialFunctionPositionRule :
    Rule("special-function-position"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != FUN) {
            return
        }

        // target special function
        if (!node.isSpecialFunction()) {
            return
        }

        // checks for violation
        val identifier = node.findChildByType(IDENTIFIER) ?: return
        node
            .siblings()
            .takeIf { siblings ->
                // in Kotlin, static members belong in companion object
                siblings.any {
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
