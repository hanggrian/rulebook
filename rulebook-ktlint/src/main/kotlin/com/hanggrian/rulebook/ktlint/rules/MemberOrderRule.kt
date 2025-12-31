package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
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
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderRule : RulebookRule(ID, MEMBER_ORDER_PROPERTY) {
    private var memberOrder = MEMBER_ORDER_PROPERTY.defaultValue
    private lateinit var memberPositions: Map<IElementType, Int>
    private lateinit var memberArguments: Map<IElementType, String>

    override val tokens: TokenSet = TokenSet.create(CLASS_BODY)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        memberOrder = editorConfig[MEMBER_ORDER_PROPERTY]
        require(memberOrder.size == 5)

        memberPositions =
            memberOrder.associate {
                when (it) {
                    "property" -> PROPERTY to 0
                    "initializer" -> CLASS_INITIALIZER to 1
                    "constructor" -> SECONDARY_CONSTRUCTOR to 2
                    "function" -> FUN to 3
                    "companion" -> OBJECT_DECLARATION to 4
                    else -> error("Unknown member type.")
                }
            }
        memberArguments =
            memberOrder.associate {
                when (it) {
                    "property" -> PROPERTY to "property"
                    "initializer" -> CLASS_INITIALIZER to "initializer"
                    "constructor" -> SECONDARY_CONSTRUCTOR to "constructor"
                    "function" -> FUN to "function"
                    "companion" -> OBJECT_DECLARATION to "companion"
                    else -> error("Unknown member type.")
                }
            }
    }

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
            if (memberPositions.getOrDefault(lastChildType, -1) > memberPositions[childType]!!) {
                emit(
                    child.startOffset,
                    Messages.get(
                        MSG,
                        memberArguments[childType]!!,
                        memberArguments[lastChildType]!!,
                    ),
                    false,
                )
            }

            lastChildType = childType
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:member-order")
        public val MEMBER_ORDER_PROPERTY: EditorConfigProperty<Set<String>> =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_member_order",
                        "The structure of a class body.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue =
                    setOf("property", "initializer", "constructor", "function", "companion"),
                propertyWriter = { it.joinToString() },
            )

        private const val MSG = "member.order"
    }
}
