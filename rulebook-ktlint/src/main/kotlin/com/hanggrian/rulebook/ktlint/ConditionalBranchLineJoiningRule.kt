package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.isWhitespaceMultipleNewline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#conditional-branch-line-joining)
 */
public class ConditionalBranchLineJoiningRule :
    Rule("conditional-branch-line-joining"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != WHEN_ENTRY) {
            return
        }

        // checks for violation
        val whitespace =
            node.treePrev
                ?.takeIf { it.elementType == WHITE_SPACE }
                ?.takeIf { it.isWhitespaceMultipleNewline() }
                ?: return
        emit(whitespace.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "conditional.branch.line.joining"
    }
}
