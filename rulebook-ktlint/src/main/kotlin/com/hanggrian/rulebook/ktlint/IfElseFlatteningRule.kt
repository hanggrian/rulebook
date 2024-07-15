package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening)
 */
public class IfElseFlatteningRule :
    Rule("if-else-flattening"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // get last if
        var `if`: ASTNode? = null
        for (child in node.children().asIterable().reversed()) {
            when (child.elementType) {
                IF -> {
                    `if` = child
                    break
                }
                WHITE_SPACE, RBRACE, EOL_COMMENT -> continue
                else -> return
            }
        }
        `if` ?: return

        // checks for violation
        val `else` = `if`.findChildByType(ELSE)
        if (`else` != null) {
            `else`.takeUnless { IF in it } ?: return
            emit(`if`.findChildByType(ELSE_KEYWORD)!!.startOffset, Messages[MSG_LIFT], false)
            return
        }
        `if`
            .findChildByType(THEN)!!
            .findChildByType(BLOCK)
            ?.children()
            ?.filter {
                it.elementType != LBRACE &&
                    it.elementType != RBRACE &&
                    it.elementType != WHITE_SPACE
            }?.takeIf { it.count() > 1 }
            ?: return
        emit(`if`.startOffset, Messages[MSG_INVERT], false)
    }

    internal companion object {
        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"
    }
}
