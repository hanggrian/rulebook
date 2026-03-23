package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BOOLEAN_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CHARACTER_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FLOAT_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTEGER_CONSTANT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.NULL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet.create

/** [See detail](https://hanggrian.github.io/rulebook/rules/#deprecated-identity) */
public class DeprecatedIdentityRule : RulebookRule(ID) {
    override val tokens: TokenSet = create(BINARY_EXPRESSION)

    override fun visit(node: ASTNode, emit: Emit) {
        // find constants
        node
            .takeIf {
                NULL in it ||
                    BOOLEAN_CONSTANT in it ||
                    FLOAT_CONSTANT in it ||
                    CHARACTER_CONSTANT in it ||
                    INTEGER_CONSTANT in it
            } ?: return

        // checks for violation
        val operationReference = node.findChildByType(OPERATION_REFERENCE) ?: return
        val operation =
            operationReference.findChildByType(EQEQEQ)
                ?: operationReference.findChildByType(EXCLEQEQEQ)
                ?: return
        emit(operation.startOffset, Messages[MSG, operation.text.dropLast(1)], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:deprecated-identity")
        private const val MSG = "deprecated.identity"
    }
}
