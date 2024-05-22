package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-illegal-naming)
 */
public class PropertyIllegalNamingRule : RulebookRule(
    "property-illegal-naming",
    setOf(ILLEGAL_PROPERTIES_PROPERTY),
) {
    private var prohibitedProperties = ILLEGAL_PROPERTIES_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        prohibitedProperties = editorConfig[ILLEGAL_PROPERTIES_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != PROPERTY &&
            node.elementType != VALUE_PARAMETER &&
            node.elementType != DESTRUCTURING_DECLARATION_ENTRY
        ) {
            return
        }

        // checks for violation
        val identifier =
            node.findChildByType(IDENTIFIER)
                ?.takeIf { it.text in prohibitedProperties }
                ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "property.illegal.naming"

        val ILLEGAL_PROPERTIES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_illegal_properties",
                        "A set of banned identifiers.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue =
                    setOf(
                        "boolean",
                        "byte",
                        "char",
                        "double",
                        "float",
                        "int",
                        "integer",
                        "long",
                        "short",
                        "string",
                        "list",
                        "set",
                        "map",
                    ),
                propertyWriter = { it.joinToString() },
            )
    }
}
