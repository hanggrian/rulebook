package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasReturnOrThrow
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening)
 */
public class ElseFlatteningRule :
    Rule("else-flattening"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != IF) {
            return
        }

        // skip single if
        if (ELSE !in node) {
            return
        }

        // checks for violation
        var lastElse: ASTNode? = null
        var currentIf: ASTNode? = node
        while (currentIf != null) {
            if (!currentIf.hasReturnOrThrow()) {
                return
            }
            lastElse = currentIf.findChildByType(ELSE_KEYWORD)
            currentIf = currentIf.findChildByType(ELSE)?.findChildByType(IF)
        }
        lastElse ?: return
        emit(lastElse.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "else.flattening"
    }
}
