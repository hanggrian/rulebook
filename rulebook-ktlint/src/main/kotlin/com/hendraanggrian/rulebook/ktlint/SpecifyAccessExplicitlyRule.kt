package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.VARIANT_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.VariantValue
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.hasNonPublicOrTestParent
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.COMPANION_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERFACE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_LITERAL
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROTECTED_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PUBLIC_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.isValOrVarKeyword
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#specify-access-explicitly).
 */
class SpecifyAccessExplicitlyRule : RulebookRule(
    "specify-access-explicitly",
    setOf(VARIANT_PROPERTY),
) {
    private var variant = VARIANT_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        variant = editorConfig[VARIANT_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // Only applies to library variant.
        if (variant != VariantValue.library) {
            return
        }

        // First line of filter.
        when (node.elementType) {
            CLASS, OBJECT_DECLARATION -> {
                // Skip literal, like `object : MyInterface { }`.
                if (node.elementType == OBJECT_DECLARATION &&
                    node.treeParent.elementType == OBJECT_LITERAL
                ) {
                    return
                }

                // Skip test class.
                val identifier = node.findChildByType(IDENTIFIER)
                if (identifier != null && identifier.text.endsWith("Test")) {
                    return
                }

                // Checks for violation.
                if (!node.hasNonPublicOrTestParent() && node.hasNoAccessModifier()) {
                    emit(
                        node.children()
                            .first {
                                it.elementType == CLASS_KEYWORD ||
                                    it.elementType == INTERFACE_KEYWORD ||
                                    it.elementType == COMPANION_KEYWORD ||
                                    it.elementType == OBJECT_KEYWORD
                            }
                            .startOffset,
                        Messages[MSG_CLASS],
                        false,
                    )
                }
            }
            FUN -> {
                // Checks for violation.
                if (!node.hasNonPublicOrTestParent() && node.hasNoAccessModifier()) {
                    emit(
                        node.children().first { it.elementType == FUN_KEYWORD }.startOffset,
                        Messages[MSG_FUNCTION],
                        false,
                    )
                }
            }
            PROPERTY -> {
                // Skip properties in code block.
                val parentType = node.treeParent?.elementType ?: return
                if (parentType != FILE && parentType != CLASS_BODY) {
                    return
                }

                // Checks for violation.
                if (!node.hasNonPublicOrTestParent() && node.hasNoAccessModifier()) {
                    emit(
                        node.children().first { it.isValOrVarKeyword() }.startOffset,
                        Messages[MSG_PROPERTY],
                        false,
                    )
                }
            }
        }
    }

    internal companion object {
        const val MSG_CLASS = "specify.access.explicitly.class"
        const val MSG_FUNCTION = "specify.access.explicitly.function"
        const val MSG_PROPERTY = "specify.access.explicitly.property"

        private fun ASTNode.hasNoAccessModifier(): Boolean {
            val modifiers = findChildByType(MODIFIER_LIST) ?: return true
            return PUBLIC_KEYWORD !in modifiers &&
                PRIVATE_KEYWORD !in modifiers &&
                INTERNAL_KEYWORD !in modifiers &&
                PROTECTED_KEYWORD !in modifiers
        }
    }
}
