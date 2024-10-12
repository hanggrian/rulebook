package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-case-branching)
 */
public class SwitchCaseBranchingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .children()
            .takeIf { nodes -> nodes.count { it.elementType == WHEN_ENTRY } == 1 }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:switch-case-branching")

        const val MSG = "switch.case.branching"
    }
}
