package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#objects-comparison).
 */
public class ObjectsComparisonRule : RulebookRule("objects-comparison") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != EQEQEQ &&
            node.elementType != EXCLEQEQEQ
        ) {
            return
        }

        // checks for violation
        val key =
            when (node.elementType) {
                EQEQEQ -> MSG_EQ
                else -> MSG_NEQ
            }
        emit(node.startOffset, Messages[key], false)
    }

    internal companion object {
        const val MSG_EQ = "objects.comparison.eq"
        const val MSG_NEQ = "objects.comparison.neq"
    }
}
