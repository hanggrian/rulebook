package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#conditional-branch-joining)
 */
public class ConditionalBranchJoiningRule : Rule("conditional-branch-joining") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != WHEN_ENTRY) {
            return
        }

        // checks for violation
        val whitespace =
            node.treePrev
                ?.takeIf { it.elementType == WHITE_SPACE }
                ?.takeIf { n -> n.text.count { it == '\n' } > 1 }
                ?: return
        emit(whitespace.endOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "conditional.branch.joining"
    }
}
