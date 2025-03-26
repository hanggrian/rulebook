package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasJumpStatement
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening) */
public class ElseFlatteningRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(IF)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip single if
        node
            .takeIf { ELSE in node }
            ?: return

        // checks for violation
        var lastElse: ASTNode? = null
        var `if`: ASTNode? = node
        while (`if` != null) {
            `if`
                .takeIf { it.hasJumpStatement() }
                ?: return
            lastElse = `if`.findChildByType(ELSE_KEYWORD)
            `if` = `if`.findChildByType(ELSE)?.findChildByType(IF)
        }
        lastElse ?: return
        emit(lastElse.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:else-flattening")

        const val MSG = "else.flattening"
    }
}
