package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#function-expression).
 */
public class FunctionExpressionRule : RulebookRule("function-expression") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != FUN) {
            return
        }

        // find single-line function
        val block = node.findChildByType(BLOCK) ?: return
        val children = block.children().filter { it.elementType != WHITE_SPACE }.toList()
        children
            .takeIf {
                it.firstOrNull()?.elementType == LBRACE &&
                    it.lastOrNull()?.elementType == RBRACE
            }
            ?.slice(1 until children.lastIndex)
            ?.takeIf { it.singleOrNull()?.elementType == RETURN }
            ?: return
        emit(block.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "function.expression"
    }
}
