package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ThrowSubclass).
 */
class ThrowSubclassRule : RulebookRule("throw-subclass") {
    internal companion object {
        const val MSG = "throw.subclass"

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
        val callExpression = node.findChildByType(CALL_EXPRESSION) ?: return

        // only target supertype
        val identifier = callExpression.findChildByType(REFERENCE_EXPRESSION)
            ?.findChildByType(IDENTIFIER) ?: return

        // report error if superclass exception is found
        if (identifier.text in AMBIGUOUS_ERRORS) {
            emit(identifier.startOffset, Messages[MSG], false)
        }
    }
}
