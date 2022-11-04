package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.CLASS_BODY
import com.pinterest.ktlint.core.ast.ElementType.EQ
import com.pinterest.ktlint.core.ast.ElementType.FILE
import com.pinterest.ktlint.core.ast.ElementType.FUN
import com.pinterest.ktlint.core.ast.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.core.ast.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * Kotlin supports function and property declaration without a return type. This return type is
 * pre-calculated during compile time.
 *
 * To avoid any ambiguity, this rule disables that behavior. This rule affects:
 * - All expression functions.
 * - Properties on top-level and within class.
 *
 * This rules ignore function/property with modifier:
 * - private
 * - internal
 */
class DeclarationReturnTypeRule : Rule("declaration-return-type-rule") {
    internal companion object {
        const val ERROR_MESSAGE = "%s need return type"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        if (node.elementType == FUN) {
            // skips function without declaration
            if (!node.hasType(EQ)) {
                return
            }
            if (!node.isValid()) {
                emit(node.startOffset, ERROR_MESSAGE.format("Expression function"), false)
            }
        } else if (node.elementType == PROPERTY) {
            // skips properties in code block
            val parentType = node.treeParent?.elementType ?: return
            if ((parentType != FILE && parentType != CLASS_BODY)) {
                return
            }
            if (!node.isValid()) {
                emit(node.startOffset, ERROR_MESSAGE.format("Property"), false)
            }
        }
    }

    private fun ASTNode.isValid(): Boolean {
        // return true if this node or its parents recursively are private
        var node: ASTNode? = this
        while (node != null) {
            if (node.isPrivate()) {
                return true
            }
            node = node.treeParent
        }
        // in case of public node, declare violation if node doesn't have a type reference
        return hasType(TYPE_REFERENCE)
    }

    private fun ASTNode.isPrivate(): Boolean {
        val modifiers = findChildByType(MODIFIER_LIST) ?: return false
        return modifiers.hasType(PRIVATE_KEYWORD) || modifiers.hasType(INTERNAL_KEYWORD)
    }

    private fun ASTNode.hasType(type: IElementType): Boolean = findChildByType(type) != null
}
