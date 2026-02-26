package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMMA
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-case) */
public class LonelyCaseRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(WHEN)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip multiple branches
        val whenEntry =
            node
                .children20
                .singleOrNull { it.elementType === WHEN_ENTRY }
                ?: return

        // checks for violation
        whenEntry
            .takeUnless { COMMA in it }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:lonely-case")
        private const val MSG = "lonely.case"
    }
}
