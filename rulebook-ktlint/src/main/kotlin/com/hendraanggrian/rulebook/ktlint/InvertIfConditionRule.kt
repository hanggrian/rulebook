package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RBRACE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#invert-if-condition).
 */
public class InvertIfConditionRule : RulebookRule("invert-if-condition") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // only proceed on one if
        val if2 = node.blockContent.singleOrNull() ?: return
        if (if2.elementType != IF) {
            return
        }

        // skip if statement with else
        if (ELSE in if2) {
            return
        }

        // obtain the inner block
        val block = if2.findChildByType(THEN)?.findChildByType(BLOCK) ?: return

        // collect whitespaces with newline
        val newLines =
            block.children()
                .filter { it.elementType == WHITE_SPACE && "\n" in it.text }
                .toList()

        // report 2 lines content
        if (newLines.size > 2) {
            emit(if2.startOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "invert.if.condition"

        private val ASTNode.blockContent
            get(): List<ASTNode> {
                val content = children().toList()
                // empty block is at least 2 node '{}'
                if (content.size < 3) {
                    return emptyList()
                }
                // iterate forward & backward
                var i = 0
                var j = content.lastIndex
                var next = content[i]
                while (next.elementType == LBRACE || next.elementType == WHITE_SPACE) {
                    next = content[++i]
                }
                next = content[j]
                while (next.elementType == RBRACE || next.elementType == WHITE_SPACE) {
                    next = content[--j]
                }
                // return only valid range
                return when {
                    i > j -> emptyList()
                    else -> content.subList(i, j + 1)
                }
            }
    }
}
