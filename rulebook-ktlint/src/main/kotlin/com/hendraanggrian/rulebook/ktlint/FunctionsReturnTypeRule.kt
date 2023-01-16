package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.ANNOTATION_ENTRY
import com.pinterest.ktlint.core.ast.ElementType.BLOCK
import com.pinterest.ktlint.core.ast.ElementType.CLASS_BODY
import com.pinterest.ktlint.core.ast.ElementType.EQ
import com.pinterest.ktlint.core.ast.ElementType.FILE
import com.pinterest.ktlint.core.ast.ElementType.FUN
import com.pinterest.ktlint.core.ast.ElementType.IDENTIFIER
import com.pinterest.ktlint.core.ast.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.core.ast.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.core.ast.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#functions-return-type).
 */
class FunctionsReturnTypeRule : Rule("functions-return-type") {
    internal companion object {
        const val ERROR_MESSAGE = "Missing return type in function '%s'."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        when (node.elementType) {
            FUN -> {
                // skip function without declaration, likely abstract
                if (EQ !in node && BLOCK !in node) {
                    return
                }

                // skip regular function
                if (BLOCK in node) {
                    return
                }

                // skip test function
                if (MODIFIER_LIST in node) {
                    for (modifier in node[MODIFIER_LIST].children()) {
                        if (modifier.elementType == ANNOTATION_ENTRY && "Test" in modifier.text) {
                            return
                        }
                    }
                }

                // check for violation
                if (node.isViolation()) {
                    emit(
                        node[VALUE_PARAMETER_LIST].endOffset,
                        ERROR_MESSAGE.format("Expression function"),
                        false
                    )
                }
            }
            PROPERTY -> {
                // skip properties without getter
                if (PROPERTY_ACCESSOR !in node) {
                    return
                }

                // skip properties in code block
                val parentType = node.treeParent?.elementType ?: return
                if (parentType != FILE && parentType != CLASS_BODY) {
                    return
                }

                // check for violation
                if (node.isViolation()) {
                    emit(
                        node[IDENTIFIER].endOffset,
                        ERROR_MESSAGE.format("Property accessor"),
                        false
                    )
                }
            }
        }
    }

    private fun ASTNode.isViolation(): Boolean {
        // return true if this node or its parents recursively are private
        var node: ASTNode? = this
        while (node != null) {
            if (node.isPrivateOrInternal()) {
                return false
            }
            node = node.treeParent
        }
        // in case of public node, declare violation if node doesn't have a type reference
        return TYPE_REFERENCE !in this
    }

    private fun ASTNode.isPrivateOrInternal(): Boolean {
        val modifiers = findChildByType(MODIFIER_LIST) ?: return false
        return PRIVATE_KEYWORD in modifiers || INTERNAL_KEYWORD in modifiers
    }
}
