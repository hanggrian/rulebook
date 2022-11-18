package com.hendraanggrian.lints.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.BLOCK
import com.pinterest.ktlint.core.ast.ElementType.CLASS_BODY
import com.pinterest.ktlint.core.ast.ElementType.EQ
import com.pinterest.ktlint.core.ast.ElementType.FILE
import com.pinterest.ktlint.core.ast.ElementType.FUN
import com.pinterest.ktlint.core.ast.ElementType.IDENTIFIER
import com.pinterest.ktlint.core.ast.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.core.ast.ElementType.PUBLIC_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.core.ast.ElementType.VALUE_PARAMETER_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See Guide](https://github.com/hendraanggrian/lints/blob/main/rules.md#function-specify-return-type).
 */
class FunctionSpecifyReturnTypeRule : Rule("function-specify-return-type") {
    internal companion object {
        const val ERROR_MESSAGE = "'%s' missing return type."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        // first line of filter
        when (node.elementType) {
            FUN -> {
                // skips regular function
                if (EQ !in node && BLOCK in node) {
                    return
                }

                // skips function without declaration, possibly abstract
                if (EQ !in node && BLOCK !in node) {
                    return
                }

                if (!node.isValid()) {
                    emit(
                        node[VALUE_PARAMETER_LIST].endOffset,
                        ERROR_MESSAGE.format("Expression function"),
                        false
                    )
                }
            }
            PROPERTY -> {
                // skips properties without getter
                if (PROPERTY_ACCESSOR !in node) {
                    return
                }

                // skips properties in code block
                val parentType = node.treeParent?.elementType ?: return
                if (parentType != FILE && parentType != CLASS_BODY) {
                    return
                }

                if (!node.isValid()) {
                    emit(
                        node[IDENTIFIER].endOffset,
                        ERROR_MESSAGE.format("Property accessor"),
                        false
                    )
                }
            }
        }
    }

    private fun ASTNode.isValid(): Boolean {
        // return true if this node or its parents recursively are private
        var node: ASTNode? = this
        while (node != null) {
            if (!node.isPublic()) {
                return true
            }
            node = node.treeParent
        }
        // in case of public node, declare violation if node doesn't have a type reference
        return TYPE_REFERENCE in this
    }

    private fun ASTNode.isPublic(): Boolean {
        val modifiers = findChildByType(MODIFIER_LIST) ?: return true
        return PUBLIC_KEYWORD in modifiers
    }
}
