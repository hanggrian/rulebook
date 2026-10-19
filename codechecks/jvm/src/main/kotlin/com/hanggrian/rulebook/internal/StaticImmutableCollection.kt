package com.hanggrian.rulebook.internal

import com.hanggrian.rulebook.ktlint.rules.Emit
import com.hanggrian.rulebook.ktlint.rules.RulebookRule
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CALL_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isPartOf
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

class StaticImmutableCollection : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(PROPERTY)

    override fun visit(node: ASTNode, emit: Emit) {
        node
            .takeIf { it.isPartOf(OBJECT_DECLARATION) }
            ?: return

        val typeIdentifier =
            node
                .findChildByType(TYPE_REFERENCE)
                ?.findChildByType(USER_TYPE)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
        if (typeIdentifier?.text in IMMUTABLE_TYPES) {
            return
        }
        val callIdentifier =
            (node.findChildByType(BINARY_EXPRESSION) ?: node)
                .findChildByType(CALL_EXPRESSION)
                ?.findChildByType(REFERENCE_EXPRESSION)
                ?.findChildByType(IDENTIFIER)
                ?.takeIf { it.text in MUTABLE_CALLS }
                ?: return
        emit(callIdentifier.startOffset, "Make collection immutable.", false)
    }

    companion object {
        val ID: RuleId = RuleId("${CodecheckRuleSet.ID.value}:static-immutable-collection")

        val MUTABLE_CALLS =
            setOf(
                "mutableListOf",
                "arrayListOf",
                "mutableSetOf",
                "linkedSetOf",
                "hashSetOf",
                "mutableMapOf",
                "linkedMapOf",
                "hashMapOf",
            )

        val IMMUTABLE_TYPES =
            setOf(
                "List",
                "Set",
                "Map",
            )
    }
}
