package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FLOAT_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-f) */
public class LowercaseFRule : RulebookRule(ID) {
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
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:lowercase-f")
        private const val MSG = "lowercase.f"
    }
}
