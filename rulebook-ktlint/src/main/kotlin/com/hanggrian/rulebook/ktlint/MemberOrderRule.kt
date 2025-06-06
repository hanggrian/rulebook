package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        // in Kotlin, static members belong in companion object
        var lastChildType: IElementType? = null
        for (child in node
            .children()
            .filter {
                it.elementType == PROPERTY ||
                    it.elementType == CLASS_INITIALIZER ||
                    it.elementType == SECONDARY_CONSTRUCTOR ||
                    it.elementType == FUN ||
                    it.elementType == OBJECT_DECLARATION
            }) {
            val childType =
                when {
                    child.elementType == PROPERTY && PROPERTY_ACCESSOR in child ->
                        // property with getter and setter is essentially a function
                        FUN

                    child.elementType == OBJECT_DECLARATION ->
                        // companion object must have appropriate keyword
                        when {
                            child.hasModifier(COMPANION_KEYWORD) -> OBJECT_DECLARATION
                            else -> continue
                        }

                    else -> child.elementType
                }

            // checks for violation
            if (MEMBER_POSITIONS.getOrDefault(lastChildType, -1) > MEMBER_POSITIONS[childType]!!) {
                emit(
                    child.startOffset,
                    Messages.get(
                        MSG,
                        MEMBER_ARGUMENTS[childType]!!,
                        MEMBER_ARGUMENTS[lastChildType]!!,
                    ),
                    false,
                )
            }

            lastChildType = childType
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:member-order")

        private const val MSG = "member.order"

        private val MEMBER_POSITIONS =
            mapOf(
                PROPERTY to 0,
                CLASS_INITIALIZER to 1,
                SECONDARY_CONSTRUCTOR to 2,
                FUN to 3,
                OBJECT_DECLARATION to 4,
            )

        private val MEMBER_ARGUMENTS =
            mapOf(
                PROPERTY to "property",
                CLASS_INITIALIZER to "initializer",
                SECONDARY_CONSTRUCTOR to "constructor",
                FUN to "function",
                OBJECT_DECLARATION to "companion",
            )
    }
}
