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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#if-statement-nesting).
 */
public class IfStatementNestingRule : RulebookRule("if-statement-nesting") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != BLOCK) {
            return
        }

        // only proceed on one if and no else
        val if2 =
            node.blockContent.singleOrNull()
                ?.takeUnless { it.elementType != IF }
                ?.takeUnless { ELSE in it }
                ?: return

        // report 2 lines content
        if2.findChildByType(THEN)?.findChildByType(BLOCK)?.children()
            ?.filter { it.elementType == WHITE_SPACE && "\n" in it.text }
            ?.toList()
            ?.takeIf { it.size > 2 }
            ?: return
        emit(if2.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "if.statement.nesting"

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
