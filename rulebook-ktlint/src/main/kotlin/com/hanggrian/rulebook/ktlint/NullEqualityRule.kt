package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQEQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.NULL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#null-equality) */
public class NullEqualityRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(BINARY_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // find null operand
        node.firstChildNode.takeIf { it.elementType == NULL }
            ?: node.lastChildNode.takeIf { it.elementType == NULL }
            ?: return

        // checks for violation
        val operator =
            node
                .findChildByType(OPERATION_REFERENCE)
                ?.firstChildNode
                ?.takeIf { it.elementType == EQEQEQ || it.elementType == EXCLEQEQEQ }
                ?: return
        emit(
            operator.startOffset,
            Messages.get(MSG, if (operator.elementType == EQEQEQ) "==" else "!="),
            false,
        )
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:null-equality")

        private const val MSG = "null.equality"
    }
}
