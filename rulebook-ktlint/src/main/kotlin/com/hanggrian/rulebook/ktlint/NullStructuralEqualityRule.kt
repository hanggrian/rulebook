package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.NULL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#null-structural-equality)
 */
public class NullStructuralEqualityRule :
    Rule("null-structural-equality"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != BINARY_EXPRESSION) {
            return
        }

        // find null operand
        node.firstChildNode.takeIf { it.elementType == NULL }
            ?: node.lastChildNode.takeIf { it.elementType == NULL }
            ?: return

        // checks for violation
        val operator =
            node
                .findChildByType(OPERATION_REFERENCE)
                ?.firstChildNode
                ?.takeIf { it.elementType == EQEQEQ || it.elementType == EXCLEQEQEQ }
                ?: return
        emit(
            operator.startOffset,
            Messages.get(MSG, if (operator.elementType == EQEQEQ) "==" else "!="),
            false,
        )
    }

    internal companion object {
        const val MSG = "null.structural.equality"
    }
}
