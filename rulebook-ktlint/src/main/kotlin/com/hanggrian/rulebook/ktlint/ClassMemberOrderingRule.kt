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

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-member-ordering) */
public class ClassMemberOrderingRule : RulebookRule(ID) {
    override val tokens: TokenSet = TokenSet.create(CLASS_BODY)

    override fun visitToken(node: ASTNode, emit: Emit) {
        var lastType: IElementType? = null
        for (child in node
            .children()
            .filter {
                it.elementType == PROPERTY ||
                    it.elementType == CLASS_INITIALIZER ||
                    it.elementType == SECONDARY_CONSTRUCTOR ||
                    it.elementType == FUN ||
                    it.elementType == OBJECT_DECLARATION
            }) {
            // in Kotlin, static members belong in companion object
            val currentType =
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
            if (MEMBER_POSITIONS.getOrDefault(lastType, -1) > MEMBER_POSITIONS[currentType]!!) {
                emit(
                    child.startOffset,
                    Messages.get(
                        MSG,
                        MEMBER_ARGUMENTS[currentType]!!,
                        MEMBER_ARGUMENTS[lastType]!!,
                    ),
                    false,
                )
            }

            lastType = currentType
        }
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:class-member-ordering")

        const val MSG = "class.member.ordering"
        private const val MSG_PROPERTY = "property"
        private const val MSG_INITIALIZER = "initializer"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"
        private const val MSG_COMPANION = "companion"

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
                PROPERTY to Messages[MSG_PROPERTY],
                CLASS_INITIALIZER to Messages[MSG_INITIALIZER],
                SECONDARY_CONSTRUCTOR to Messages[MSG_CONSTRUCTOR],
                FUN to Messages[MSG_FUNCTION],
                OBJECT_DECLARATION to Messages[MSG_COMPANION],
            )
    }
}
