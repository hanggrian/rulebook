package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.CLASS_BODY
import com.pinterest.ktlint.core.ast.ElementType.EQ
import com.pinterest.ktlint.core.ast.ElementType.FILE
import com.pinterest.ktlint.core.ast.ElementType.FUN
import com.pinterest.ktlint.core.ast.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY
import com.pinterest.ktlint.core.ast.ElementType.PUBLIC_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * **Guide**: [Specify Return Type](https://github.com/hendraanggrian/codestyle/blob/main/guides/specify-return-type.md).
 */
class SpecifyReturnTypeRule : Rule("declaration-return-type-rule") {
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
            if (!node.isPublic()) {
                return true
            }
            node = node.treeParent
        }
        // in case of public node, declare violation if node doesn't have a type reference
        return this.hasType(TYPE_REFERENCE)
    }

    private fun ASTNode.isPublic(): Boolean {
        val modifiers = findChildByType(MODIFIER_LIST) ?: return true
        return modifiers.hasType(PUBLIC_KEYWORD)
    }

    private fun ASTNode.hasType(type: IElementType): Boolean = findChildByType(type) != null
}
