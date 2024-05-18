package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-common-naming)
 */
public class GenericsCommonNamingRule : RulebookRule(
    "generics-common-naming",
    setOf(COMMON_GENERICS_PROPERTY),
) {
    private var commonGenerics = COMMON_GENERICS_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        commonGenerics = editorConfig[COMMON_GENERICS_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != CLASS && node.elementType != FUN) {
            return
        }

        // filter out multiple generics
        val typeParameter =
            node.findChildByType(TYPE_PARAMETER_LIST)?.children()
                ?.singleOrNull { it.elementType == TYPE_PARAMETER }
                ?: return

        // checks for violation
        val identifier =
            typeParameter.findChildByType(IDENTIFIER)
                ?.takeIf { !node.hasParentWithGenerics() && it.text !in commonGenerics }
                ?: return
        emit(identifier.startOffset, Messages.get(MSG, commonGenerics.joinToString()), false)
    }

    internal companion object {
        const val MSG = "generics.common.naming"

        val COMMON_GENERICS_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_common_generics",
                        "A set of common generics.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("E", "K", "N", "T", "V"),
                propertyWriter = { it.joinToString() },
            )

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            var next: ASTNode? = treeParent
            while (next != null) {
                if (TYPE_PARAMETER_LIST in next) {
                    return true
                }
                next = next.treeParent
            }
            return false
        }
    }
}
