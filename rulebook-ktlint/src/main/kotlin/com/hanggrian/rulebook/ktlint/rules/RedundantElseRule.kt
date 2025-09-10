package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.hasJumpStatement
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-else) */
public class RedundantElseRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(IF)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip single if
        node
            .takeIf { ELSE in node }
            ?: return

        // checks for violation
        var `if`: ASTNode? = node
        while (`if` != null) {
            `if`
                .findChildByType(THEN)
                ?.takeIf { it.hasJumpStatement() }
                ?: return
            val lastElse = `if`.findChildByType(ELSE_KEYWORD)
            if (lastElse != null) {
                emit(lastElse.startOffset, Messages[MSG], false)
            }
            `if` = `if`.findChildByType(ELSE)?.findChildByType(IF)
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:redundant-else")

        private const val MSG = "redundant.else"
    }
}
