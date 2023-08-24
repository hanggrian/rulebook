package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/DescribeThrow).
 */
class DescribeThrowRule : RulebookRule("describe-throw") {
    internal companion object {
        const val MSG = "describe.throw"

        val AMBIGUOUS_ERRORS = setOf("Exception", "Error", "Throwable")
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != THROW) {
            return
        }

        // only target declaration, reference such as `val error = Exception()` is ignored
        val callExpression = node.getOrNull(CALL_EXPRESSION) ?: return

        // only target supertype
        val identifier = callExpression.getOrNull(REFERENCE_EXPRESSION)
            ?.getOrNull(IDENTIFIER) ?: return
        if (identifier.text !in AMBIGUOUS_ERRORS) {
            return
        }

        // report error if there is no message
        val valueArgumentList = callExpression.getOrNull(VALUE_ARGUMENT_LIST) ?: return
        if (VALUE_ARGUMENT !in valueArgumentList) {
            emit(identifier.startOffset, Messages.get(MSG, identifier.text), false)
        }
    }
}
