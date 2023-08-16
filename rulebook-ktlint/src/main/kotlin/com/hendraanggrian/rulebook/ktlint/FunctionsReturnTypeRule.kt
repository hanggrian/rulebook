package com.hendraanggrian.rulebook.ktlint

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
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY_ACCESSOR
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/FunctionsReturnType).
 */
class FunctionsReturnTypeRule : RulebookRule("functions-return-type") {
    internal companion object {
        const val MSG_EXPR = "functions.return.type.expr"
        const val MSG_PROP = "functions.return.type.prop"
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
                    val identifier = node.findChildByType(IDENTIFIER)!!
                    emit(
                        node[VALUE_PARAMETER_LIST].endOffset,
                        Messages.get(MSG_EXPR, identifier.text),
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
                    val identifier = node.findChildByType(IDENTIFIER)!!
                    emit(
                        node[IDENTIFIER].endOffset,
                        Messages.get(MSG_PROP, identifier.text),
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
