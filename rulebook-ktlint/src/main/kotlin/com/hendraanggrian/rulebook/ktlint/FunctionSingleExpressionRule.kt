package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtReturnExpression

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#function-single-expression)
 */
public class FunctionSingleExpressionRule : RulebookRule("function-single-expression") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != FUN && node.elementType != PROPERTY_ACCESSOR) {
            return
        }

        // checks for violation
        val block =
            (node.findChildByType(BLOCK) as? KtBlockExpression)
                ?.takeIf { b -> b.children.singleOrNull() is KtReturnExpression }
                ?: return
        emit(block.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "function.single.expression"
    }
}
