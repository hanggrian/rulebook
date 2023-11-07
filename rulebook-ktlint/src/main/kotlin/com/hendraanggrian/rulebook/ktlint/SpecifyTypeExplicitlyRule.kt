package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.ANNOTATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#specify-type-explicitly).
 */
class SpecifyTypeExplicitlyRule : RulebookRule("specify-type-explicitly") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // First line of filter.
        when (node.elementType) {
            FUN -> {
                // Skip function without declaration, likely abstract.
                if (EQ !in node && BLOCK !in node) {
                    return
                }

                // Skip regular function.
                if (BLOCK in node) {
                    return
                }

                // Skip test function.
                val modifierList = node.findChildByType(MODIFIER_LIST)
                if (modifierList != null) {
                    for (modifier in modifierList.children()) {
                        if (modifier.elementType == ANNOTATION_ENTRY && "Test" in modifier.text) {
                            return
                        }
                    }
                }

                // Checks for violation.
                if (!node.hasNonPublicParent() && TYPE_REFERENCE !in node) {
                    val valueParameterList = node.findChildByType(VALUE_PARAMETER_LIST)!!
                    emit(valueParameterList.endOffset, Messages[MSG_FUNCTION], false)
                }
            }
            PROPERTY -> {
                // Skip properties in code block.
                val parentType = node.treeParent?.elementType ?: return
                if (parentType != FILE && parentType != CLASS_BODY) {
                    return
                }

                // Checks for violation.
                if (!node.hasNonPublicParent() && TYPE_REFERENCE !in node) {
                    val identifier = node.findChildByType(IDENTIFIER)!!
                    emit(identifier.endOffset, Messages[MSG_PROPERTY], false)
                }
            }
        }
    }

    internal companion object {
        const val MSG_FUNCTION = "specify.type.explicitly.function"
        const val MSG_PROPERTY = "specify.type.explicitly.property"

        private fun ASTNode.hasNonPublicParent(): Boolean {
            var next: ASTNode? = this
            while (next != null) {
                if (next.isNonPublic()) {
                    return true
                }
                next = next.treeParent
            }
            return false
        }

        private fun ASTNode.isNonPublic(): Boolean {
            val modifiers = findChildByType(MODIFIER_LIST) ?: return false
            return PRIVATE_KEYWORD in modifiers || INTERNAL_KEYWORD in modifiers
        }
    }
}
