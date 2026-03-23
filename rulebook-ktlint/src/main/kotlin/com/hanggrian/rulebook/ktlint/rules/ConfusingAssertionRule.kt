package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.twoWayMapOf
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PREFIX_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_ARGUMENT_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#confusing-assertion) */
public class ConfusingAssertionRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(CALL_EXPRESSION)

    override fun isTest(): Boolean = true

    override fun visit(node: ASTNode, emit: Emit) {
        // find inverted assert function
        val functionReplacement =
            node
                .findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.run { ASSERT_CALLS[text] }
                ?: return

        // checks for violation
        node
            .findChildByType(VALUE_ARGUMENT_LIST)
            ?.findChildByType(VALUE_ARGUMENT)
            ?.findChildByType(PREFIX_EXPRESSION)
            ?.findChildByType(OPERATION_REFERENCE)
            ?.takeIf { EXCL in it }
            ?: return
        emit(node.startOffset, Messages[MSG, functionReplacement], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:confusing-assertion")
        private const val MSG = "confusing.assertion"

        private val ASSERT_CALLS = twoWayMapOf("assertTrue" to "assertFalse")
    }
}
