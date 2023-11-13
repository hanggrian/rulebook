package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.VARIANT_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.VariantValue
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.hendraanggrian.rulebook.ktlint.internals.hasNonPublicOrTestParent
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EQ
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#specify-type-explicitly).
 */
class SpecifyTypeExplicitlyRule : RulebookRule(
    "specify-type-explicitly",
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
            FUN -> {
                // Skip function without declaration, likely abstract.
                if (EQ !in node && BLOCK !in node) {
                    return
                }

                // Skip regular function.
                if (BLOCK in node) {
                    return
                }

                // Checks for violation.
                if (!node.hasNonPublicOrTestParent() && TYPE_REFERENCE !in node) {
                    emit(
                        node.findChildByType(VALUE_PARAMETER_LIST)!!.endOffset,
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
                if (!node.hasNonPublicOrTestParent() && TYPE_REFERENCE !in node) {
                    emit(
                        node.findChildByType(IDENTIFIER)!!.endOffset,
                        Messages[MSG_PROPERTY],
                        false,
                    )
                }
            }
        }
    }

    internal companion object {
        const val MSG_FUNCTION = "specify.type.explicitly.function"
        const val MSG_PROPERTY = "specify.type.explicitly.property"
    }
}
