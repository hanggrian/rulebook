package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/ThrowAmbiguity).
 */
class ThrowAmbiguityRule : RulebookRule("throw-ambiguity") {
    internal companion object {
        const val MSG = "throw.ambiguity"

        private val IDENTIFIER_TARGETS = setOf("Exception", "Error", "Throwable")
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
        if (identifier.text !in IDENTIFIER_TARGETS) {
            return
        }

        // report error if there is no message
        if (VALUE_ARGUMENT !in callExpression[VALUE_ARGUMENT_LIST]) {
            emit(identifier.startOffset, Messages.get(MSG, identifier.text), false)
        }
    }
}
