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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#string-interpolation)
 */
public class StringInterpolationRule : RulebookRule("string-interpolation") {
    public override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != BINARY_EXPRESSION) {
            return
        }

        // only target root expression
        node.takeUnless { it.treeParent.elementType == BINARY_EXPRESSION } ?: return

        // only target consecutive concat
        var isString = false
        var plusCount = 0
        var next: ASTNode? = node
        while (next != null) {
            if (!isString) {
                isString = STRING_TEMPLATE in next
            }
            if (next.findChildByType(OPERATION_REFERENCE)
                    ?.children()?.singleOrNull()
                    ?.elementType == PLUS
            ) {
                plusCount++
            }
            next = next.firstChildNode

            // checks for violation
            if (isString && plusCount > 1) {
                emit(node.startOffset, Messages[MSG], false)
                return
            }
        }
    }

    internal companion object {
        const val MSG = "string.interpolation"
    }
}
