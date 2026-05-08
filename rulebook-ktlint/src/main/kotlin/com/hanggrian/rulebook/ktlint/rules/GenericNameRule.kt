package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#generic-name) */
public class GenericNameRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(TYPE_PARAMETER)

    override fun visit(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(IDENTIFIER)
                ?: return
        identifier
            .takeUnless { PASCAL_CASE_REGEX.matches(it.text) }
            ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:generic-name")
        private const val MSG = "generic.name"

        private val PASCAL_CASE_REGEX = Regex("^[A-Z](?![A-Z0-9]+$)[a-zA-Z0-9]*$|^[A-Z]\\d*$")
    }
}
