package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.BLOCK
import com.pinterest.ktlint.core.ast.ElementType.FUN
import com.pinterest.ktlint.core.ast.ElementType.PROPERTY
import com.pinterest.ktlint.core.ast.ElementType.TYPE_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/** Expression function and property declaration must state its return type. */
class DeclarationReturnTypeRule : Rule("declaration-return-type-rule") {
    internal companion object {
        const val ERROR_MESSAGE = "%s need return type"
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        val typeName = when {
            node.elementType == FUN && node.findChildByType(BLOCK) == null -> "Expression function"
            node.elementType == PROPERTY -> "Property"
            else -> return
        }

        if (node.findChildByType(TYPE_REFERENCE) == null) {
            emit(node.startOffset, ERROR_MESSAGE.format(typeName), false)
        }
    }
}
