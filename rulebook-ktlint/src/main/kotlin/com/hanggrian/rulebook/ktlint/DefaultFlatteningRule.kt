package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasReturnOrThrow
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening)
 */
public class DefaultFlatteningRule :
    Rule("default-flattening"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != WHEN) {
            return
        }

        // skip no default
        val cases = node.children().filter { it.elementType == WHEN_ENTRY }
        val default = cases.lastOrNull()?.takeIf { ELSE_KEYWORD in it } ?: return

        // checks for violation
        cases
            .toList()
            .dropLast(1)
            .takeIf { case -> case.all { it.hasReturnOrThrow() } }
            ?: return
        emit(default.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "default.flattening"
    }
}
