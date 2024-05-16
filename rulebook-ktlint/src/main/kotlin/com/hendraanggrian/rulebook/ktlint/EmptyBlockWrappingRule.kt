package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-block-wrapping)
 */
public class EmptyBlockWrappingRule : RulebookRule("empty-block-wrapping") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // checks for violation
        val children = node.children().toList()
        children
            .takeIf {
                it.firstOrNull()?.elementType == LBRACE &&
                    it.lastOrNull()?.elementType == RBRACE
            }
            ?.slice(1 until children.lastIndex)
            ?.takeIf { n -> n.isNotEmpty() && n.all { it.elementType == WHITE_SPACE } }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "empty.block.wrapping"
    }
}
