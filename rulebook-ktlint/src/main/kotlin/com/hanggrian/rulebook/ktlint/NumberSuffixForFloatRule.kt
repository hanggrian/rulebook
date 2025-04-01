package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FLOAT_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-float) */
public class NumberSuffixForFloatRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(FLOAT_LITERAL)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .text
            .last()
            .takeIf { it == 'F' }
            ?: return
        emit(node.endOffset, Messages[MSG_NUM], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:number-suffix-for-float")

        const val MSG_NUM = "number.suffix.for.float"
    }
}
