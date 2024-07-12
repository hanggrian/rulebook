package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.lastIf
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-flattening)
 */
public class IfFlatteningRule :
    Rule("if-flattening"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // checks for violation
        val `if` = node.lastIf ?: return
        `if`
            .takeIf { ELSE !in it }
            ?.findChildByType(THEN)
            ?.findChildByType(BLOCK)
            ?.children()
            ?.filter {
                it.elementType != LBRACE &&
                    it.elementType != RBRACE &&
                    it.elementType != WHITE_SPACE
            }?.takeIf { it.count() > 1 }
            ?: return
        emit(`if`.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "if.flattening"
    }
}
