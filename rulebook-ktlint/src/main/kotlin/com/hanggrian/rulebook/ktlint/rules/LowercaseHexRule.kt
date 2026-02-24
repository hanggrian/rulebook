package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTEGER_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-hex) */
public class LowercaseHexRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(INTEGER_LITERAL)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val value =
            node
                .text
                .takeIf { it.startsWith("0x", true) }
                ?: return
        val valueReplacement =
            value
                .lowercase()
                .takeUnless { it == value }
                ?: return
        emit(node.startOffset, Messages[MSG, valueReplacement], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:lowercase-hex")
        private const val MSG = "lowercase.hex"
    }
}
