package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.core.ast.ElementType.IDENTIFIER
import com.pinterest.ktlint.core.ast.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.core.ast.ElementType.THROW
import com.pinterest.ktlint.core.ast.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.core.ast.ElementType.VALUE_ARGUMENT_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#exception-ambiguity).
 */
class ExceptionAmbiguityRule : Rule("exception-ambiguity") {
    internal companion object {
        const val ERROR_MESSAGE = "Ambiguous exception '%s'."
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
        val callExpression = node.findChildByType(CALL_EXPRESSION) ?: return

        // only target supertype
        val identifier = callExpression[REFERENCE_EXPRESSION][IDENTIFIER]
        if (identifier.text != "Exception" && identifier.text != "Error" &&
            identifier.text != "Throwable"
        ) {
            return
        }

        // report error if there is no message
        if (VALUE_ARGUMENT !in callExpression[VALUE_ARGUMENT_LIST]) {
            emit(identifier.startOffset, ERROR_MESSAGE.format(identifier.text), false)
        }
    }
}
