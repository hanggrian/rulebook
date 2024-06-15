package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#operand-structural-equality)
 */
public class OperandStructuralEqualityRule : Rule("operand-structural-equality") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // checks for violation
        when (node.elementType) {
            EQEQEQ -> emit(node.startOffset, Messages[MSG_EQ], false)
            EXCLEQEQEQ -> emit(node.startOffset, Messages[MSG_NEQ], false)
        }
    }

    internal companion object {
        const val MSG_EQ = "operand.structural.equality.eq"
        const val MSG_NEQ = "operand.structural.equality.neq"
    }
}
