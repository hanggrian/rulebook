package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUNCTION_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LAMBDA_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.STRING_TEMPLATE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-configuration) */
public class LonelyConfigurationRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(CALL_EXPRESSION)

    override fun isScript(): Boolean = true

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target named domain or handler with scope
        node
            .findChildByType(REFERENCE_EXPRESSION)
            ?.findChildByType(IDENTIFIER)
            ?.takeIf { it.text in NAMED_DOMAIN_FUNCTIONS }
            ?: return

        // checks for violation
        val statement =
            node
                .findChildByType(LAMBDA_ARGUMENT)
                ?.findChildByType(LAMBDA_EXPRESSION)
                ?.findChildByType(FUNCTION_LITERAL)
                ?.findChildByType(BLOCK)
                ?.children20
                ?.singleOrNull()
                ?.takeIf {
                    it.elementType === DOT_QUALIFIED_EXPRESSION ||
                        it.elementType === CALL_EXPRESSION
                }?.takeUnless { it.elementType === CALL_EXPRESSION && STRING_TEMPLATE in it }
                ?: return
        emit(statement.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:lonely-configuration")
        private const val MSG = "lonely.configuration"

        private val NAMED_DOMAIN_FUNCTIONS =
            hashSetOf("repositories", "dependencies", "configurations", "tasks", "sourceSets")
    }
}
