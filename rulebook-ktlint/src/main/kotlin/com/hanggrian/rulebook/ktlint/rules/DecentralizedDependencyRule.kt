package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.LITERAL_STRING_TEMPLATE_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REGULAR_STRING_PART
import com.pinterest.ktlint.rule.engine.core.api.ElementType.STRING_TEMPLATE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.parent
import com.pinterest.ktlint.rule.engine.core.api.prevSibling
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#decentralized-dependency) */
public class DecentralizedDependencyRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(CALL_EXPRESSION)

    override fun isScript(): Boolean = true

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target dependencies block
        val calleeIdentifier =
            node
                .prevSibling { it.elementType === REFERENCE_EXPRESSION }
                ?.findChildByType(IDENTIFIER)
                ?: node
                    .parent {
                        it.elementType === CALL_EXPRESSION ||
                            it.elementType === DOT_QUALIFIED_EXPRESSION
                    }?.findChildByType(REFERENCE_EXPRESSION)
                    ?.findChildByType(IDENTIFIER)
                ?: return
        calleeIdentifier
            .takeIf { it.text == "dependencies" }
            ?: return

        // checks for violation
        val stringTemplate =
            node
                .findChildByType(VALUE_ARGUMENT_LIST)
                ?.children20
                ?.singleOrNull { it.elementType === VALUE_ARGUMENT }
                ?.findChildByType(STRING_TEMPLATE)
                ?: return
        stringTemplate
            .findChildByType(LITERAL_STRING_TEMPLATE_ENTRY)
            ?.findChildByType(REGULAR_STRING_PART)
            ?: return
        emit(stringTemplate.startOffset, Messages[MSG], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:decentralized-dependency")
        private const val MSG = "decentralized.dependency"
    }
}
