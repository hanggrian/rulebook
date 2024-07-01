package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Emit
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#if-statement-flattening)
 */
public class IfStatementFlatteningRule :
    Rule("if-statement-flattening"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // only proceed on one if and no else
        val children = node.children().toList()
        val if2 =
            children
                .takeIf {
                    it.firstOrNull()?.elementType == LBRACE &&
                        it.lastOrNull()?.elementType == RBRACE
                }?.slice(1 until children.lastIndex)
                ?.singleOrNull { it.elementType != WHITE_SPACE }
                ?.takeIf { it.elementType == IF }
                ?.takeUnless { ELSE in it }
                ?: return

        // checks for violation
        if2
            .findChildByType(THEN)
            ?.findChildByType(BLOCK)
            ?.children()
            ?.filter { it.elementType == WHITE_SPACE && "\n" in it.text }
            ?.toList()
            ?.takeIf { it.size > 2 }
            ?: return
        emit(if2.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "if.statement.flattening"
    }
}
