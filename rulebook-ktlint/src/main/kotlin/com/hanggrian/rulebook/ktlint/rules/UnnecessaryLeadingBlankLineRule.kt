package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-leading-blank-line) */
public class UnnecessaryLeadingBlankLineRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(FILE)

    override fun visit(node: ASTNode, emit: Emit) {
        // checks for violation
        val whitespace =
            node
                .firstChildNode
                ?.takeIf { it.isWhiteSpaceWithNewline20 }
                ?: return
        emit(whitespace.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId =
            RuleId("${RulebookRuleSet.ID.value}:unnecessary-leading-blank-line")
        private const val MSG = "unnecessary.leading.blank.line"
    }
}
