package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtIfExpression

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
            (node as KtBlockExpression).children.singleOrNull()
                ?.takeIf { (it as? KtIfExpression)?.`else` == null }
                ?.node
                ?: return

        // report 2 statements content
        (if2.findChildByType(THEN)?.findChildByType(BLOCK) as? KtBlockExpression)
            ?.takeIf { it.statements.size > 1 }
            ?: return
        emit(if2.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "if.statement.nesting"
    }
}
