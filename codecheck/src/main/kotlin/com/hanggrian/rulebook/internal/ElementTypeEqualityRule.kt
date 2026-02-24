package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DOT_QUALIFIED_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EXCLEQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OPERATION_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.nextSibling20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

class ElementTypeEqualityRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(BINARY_EXPRESSION)

    override fun visitToken(node: ASTNode, emit: Emit) {
        node
            .psi
            .containingFile
            .takeIf { "rulebook-ktlint" in it.name }
            ?: return
        val referenceExpression =
            node
                .findChildByType(DOT_QUALIFIED_EXPRESSION)
                ?.findChildByType(DOT)
                ?.nextSibling20
                ?.takeIf { it.elementType == REFERENCE_EXPRESSION }
                ?: node.findChildByType(REFERENCE_EXPRESSION)
                ?: return
        referenceExpression
            .findChildByType(IDENTIFIER)
            ?.takeIf { it.text == "elementType" }
            ?: return

        val operationReference =
            node
                .findChildByType(OPERATION_REFERENCE)
                ?: return
        val equality =
            operationReference.findChildByType(EQEQ)
                ?: operationReference.findChildByType(EXCLEQ)
                ?: return
        emit(
            equality.startOffset,
            "Compare 'elementType' with '${
                when (equality.elementType) {
                    EQEQ -> "==="
                    else -> "!=="
                }
            }'.",
            false,
        )
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:element-type-equality")
    }
}
