package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-multiple-branching)
 */
public class SwitchMultipleBranchingRule :
    Rule("switch-multiple-branching"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != WHEN) {
            return
        }

        // checks for violation
        node
            .children()
            .filter { it.elementType == WHEN_ENTRY }
            .takeIf { it.count() < 2 }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "switch.multiple.branching"
    }
}
