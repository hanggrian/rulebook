package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.lastIf
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
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
        if (node.elementType != BLOCK) {
            return
        }

        // checks for violation
        val `if` = node.lastIf ?: return
        `if`
            .takeIf { IF !in (`if`.findChildByType(ELSE) ?: return) }
            ?.findChildByType(THEN)
            ?.findChildByType(BLOCK)
            ?.takeIf { RETURN in it || THROW in it }
            ?: return
        emit(`if`.findChildByType(ELSE_KEYWORD)!!.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "else.flattening"
    }
}
