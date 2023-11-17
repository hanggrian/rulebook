package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PLUS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.STRING_TEMPLATE
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#use-string-templates).
 */
public class UseStringTemplatesRule : RulebookRule("use-string-templates") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != BINARY_EXPRESSION) {
            return
        }

        // only target expression with string literal
        if (STRING_TEMPLATE !in node) {
            return
        }

        // checks for violation
        val operationReference = node.findChildByType(OPERATION_REFERENCE) ?: return
        if (operationReference.children().singleOrNull()?.elementType == PLUS) {
            emit(node.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "use.string.templates"
    }
}
