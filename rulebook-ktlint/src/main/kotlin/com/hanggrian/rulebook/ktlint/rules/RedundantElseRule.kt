package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.hasJumpStatement
import com.hanggrian.rulebook.ktlint.isChildOfProperty
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.parent
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-else) */
public class RedundantElseRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(IF)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip property assignment
        node
            .takeUnless { it.isChildOfProperty() }
            ?: return

        // target root if
        node
            .takeUnless { it.parent?.elementType === ELSE }
            ?: return

        // checks for violation
        var `if`: ASTNode? = node
        while (`if` != null) {
            `if`
                .findChildByType(THEN)
                ?.takeIf { it.hasJumpStatement() }
                ?: return
            val `else` =
                `if`
                    .findChildByType(ELSE)
                    ?: return
            emit(`if`.findChildByType(ELSE_KEYWORD)!!.startOffset, Messages[MSG], false)
            `if` = `else`.findChildByType(IF)
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:redundant-else")
        private const val MSG = "redundant.else"
    }
}
