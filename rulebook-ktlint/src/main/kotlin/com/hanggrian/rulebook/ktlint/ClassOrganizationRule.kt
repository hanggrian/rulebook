package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.hasModifier
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_INITIALIZER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.SECONDARY_CONSTRUCTOR
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-organization)
 */
public class ClassOrganizationRule :
    Rule("class-organization"),
    RuleAutocorrectApproveHandler {
    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS_BODY) {
            return
        }

        var lastType: IElementType? = null
        for (child in node.children()) {
            // capture child types
            if (child.elementType != PROPERTY &&
                child.elementType != CLASS_INITIALIZER &&
                child.elementType != SECONDARY_CONSTRUCTOR &&
                child.elementType != FUN &&
                child.elementType != OBJECT_DECLARATION
            ) {
                continue
            }

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
            if (ELEMENT_POSITIONS.getOrDefault(lastType, -1) > ELEMENT_POSITIONS[currentType]!!) {
                emit(
                    child.startOffset,
                    Messages.get(
                        MSG,
                        ELEMENT_ARGUMENTS[currentType]!!,
                        ELEMENT_ARGUMENTS[lastType]!!,
                    ),
                    false,
                )
            }

            lastType = currentType
        }
    }

    internal companion object {
        const val MSG = "class.organization"
        private const val MSG_PROPERTY = "property"
        private const val MSG_INITIALIZER = "initializer"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"
        private const val MSG_COMPANION = "companion"

        private val ELEMENT_POSITIONS =
            mapOf(
                PROPERTY to 0,
                CLASS_INITIALIZER to 1,
                SECONDARY_CONSTRUCTOR to 2,
                FUN to 3,
                OBJECT_DECLARATION to 4,
            )

        private val ELEMENT_ARGUMENTS =
            mapOf(
                PROPERTY to Messages[MSG_PROPERTY],
                CLASS_INITIALIZER to Messages[MSG_INITIALIZER],
                SECONDARY_CONSTRUCTOR to Messages[MSG_CONSTRUCTOR],
                FUN to Messages[MSG_FUNCTION],
                OBJECT_DECLARATION to Messages[MSG_COMPANION],
            )
    }
}
