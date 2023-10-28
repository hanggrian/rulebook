package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#throw-exception-subclass).
 */
class ThrowExceptionSubclassRule : RulebookRule("throw-exception-subclass") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != THROW) {
            return
        }

        // only target declaration, reference such as `val error = Exception()` is ignored
        val callExpression = node.findChildByType(CALL_EXPRESSION) ?: return

        // only target supertype
        val identifier =
            callExpression.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER) ?: return

        // report error if superclass exception is found
        if (identifier.text in AMBIGUOUS_ERRORS) {
            emit(identifier.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "throw.exception.subclass"

        private val AMBIGUOUS_ERRORS = setOf("Exception", "Error", "Throwable")
    }
}
