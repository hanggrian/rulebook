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

        // checks for violation
        val operationReference = node.findChildByType(OPERATION_REFERENCE) ?: return
        process(operationReference, node.firstChildNode, emit)
        process(operationReference, node.lastChildNode, emit)
    }

    internal companion object {
        const val MSG = "null.structural.equality"

        private fun process(operator: ASTNode, operand: ASTNode, emit: Emit) {
            if (operand.elementType == NULL) {
                emit(
                    operator.firstChildNode.startOffset,
                    Messages.get(
                        MSG,
                        when {
                            EQEQEQ in operator -> "=="
                            EXCLEQEQEQ in operator -> "!="
                            else -> return
                        },
                    ),
                    false,
                )
            }
        }
    }
}
