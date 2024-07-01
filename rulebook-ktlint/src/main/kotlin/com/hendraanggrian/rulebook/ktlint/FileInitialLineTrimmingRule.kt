package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Emit
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#file-initial-line-trimming)
 */
public class FileInitialLineTrimmingRule :
    Rule("file-initial-line-trimming"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != FILE) {
            return
        }

        // checks for violation
        node.firstChildNode?.takeIf { it.elementType == WHITE_SPACE } ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "file.initial.line.trimming"
    }
}
