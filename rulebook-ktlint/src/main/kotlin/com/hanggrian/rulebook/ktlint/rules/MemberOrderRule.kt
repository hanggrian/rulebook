package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.properties.MEMBER_ORDER_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.hasModifier
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#member-order) */
public class MemberOrderRule : RulebookRule(ID, MEMBER_ORDER_PROPERTY) {
    private var memberOrder = MEMBER_ORDER_PROPERTY.defaultValue
    private var propertyPosition = 0
    private var initializerPosition = 1
    private var constructorPosition = 2
    private var functionPosition = 3
    private var companionPosition = 4

    override val tokens: TokenSet = TokenSet.create(CLASS_BODY)

    private val ASTNode.memberPosition: Int?
        get() =
            when (elementType) {
                PROPERTY -> if (PROPERTY_ACCESSOR in this) functionPosition else propertyPosition
                CLASS_INITIALIZER -> initializerPosition
                SECONDARY_CONSTRUCTOR -> constructorPosition
                FUN -> functionPosition
                else -> if (hasModifier(COMPANION_KEYWORD)) companionPosition else null
            }

    private val ASTNode.memberArgument: String?
        get() =
            when (elementType) {
                PROPERTY -> if (PROPERTY_ACCESSOR in this) "function" else "property"
                CLASS_INITIALIZER -> "initializer"
                SECONDARY_CONSTRUCTOR -> "constructor"
                FUN -> "function"
                else -> if (hasModifier(COMPANION_KEYWORD)) "companion object" else null
            }

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        memberOrder = editorConfig[MEMBER_ORDER_PROPERTY]
        require(memberOrder.size == 5)

        propertyPosition = memberOrder.indexOf("property")
        initializerPosition = memberOrder.indexOf("initializer")
        constructorPosition = memberOrder.indexOf("constructor")
        functionPosition = memberOrder.indexOf("function")
        companionPosition = memberOrder.indexOf("companion")
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // in Kotlin, static members belong in companion object
        var lastChild: ASTNode? = null
        for (child in node
            .children20
            .filter {
                it.elementType === PROPERTY ||
                    it.elementType === CLASS_INITIALIZER ||
                    it.elementType === SECONDARY_CONSTRUCTOR ||
                    it.elementType === FUN ||
                    it.elementType === OBJECT_DECLARATION
            }) {
            // checks for violation
            val childMemberPosition = child.memberPosition ?: continue
            if ((lastChild?.memberPosition ?: -1) > childMemberPosition) {
                emit(
                    child.startOffset,
                    Messages[MSG, child.memberArgument!!, lastChild!!.memberArgument!!],
                    false,
                )
            }

            lastChild = child
        }
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:member-order")
        private const val MSG = "member.order"
    }
}
