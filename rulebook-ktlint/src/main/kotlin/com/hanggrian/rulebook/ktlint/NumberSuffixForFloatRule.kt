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
        emit(node.endOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:number-suffix-for-float")

        private const val MSG = "number.suffix.for.float"
    }
}
