package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.properties.ILLEGAL_VARIABLE_NAMES_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-variable-name) */
public class IllegalVariableNameRule : RulebookRule(ID, ILLEGAL_VARIABLE_NAMES_PROPERTY) {
    private var illegalVariableNames = ILLEGAL_VARIABLE_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet =
        create(
            PROPERTY,
            VALUE_PARAMETER,
            DESTRUCTURING_DECLARATION_ENTRY,
        )

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        illegalVariableNames = editorConfig[ILLEGAL_VARIABLE_NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(IDENTIFIER)
                ?.takeIf {
                    when {
                        !it.text.startsWith('`') || !it.text.endsWith('`') -> it.text
                        else -> it.text.substring(1, it.text.lastIndex)
                    } in illegalVariableNames
                } ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:illegal-variable-name")
        private const val MSG = "illegal.variable.name"
    }
}
