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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#use-expression-function).
 */
public class UseExpressionFunctionRule : RulebookRule("use-expression-function") {
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
        block.children().filter { it.elementType != WHITE_SPACE }.toList()
            .takeIf { it.firstOrNull()?.elementType == LBRACE }
            ?.takeIf { it.lastOrNull()?.elementType == RBRACE }
            ?.takeIf { it.getOrNull(1)?.elementType == RETURN }
            ?: return
        emit(block.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "use.expression.function"
    }
}
