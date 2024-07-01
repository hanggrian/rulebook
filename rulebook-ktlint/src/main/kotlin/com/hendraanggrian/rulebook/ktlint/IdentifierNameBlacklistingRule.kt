package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Emit
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#identifier-name-blacklisting)
 */
public class IdentifierNameBlacklistingRule :
    Rule(
        "identifier-name-blacklisting",
        setOf(NAMES_PROPERTY),
    ),
    RuleAutocorrectApproveHandler {
    private var names = NAMES_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[NAMES_PROPERTY]
    }

    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != PROPERTY &&
            node.elementType != VALUE_PARAMETER &&
            node.elementType != DESTRUCTURING_DECLARATION_ENTRY
        ) {
            return
        }

        // checks for violation
        val identifier =
            node
                .findChildByType(IDENTIFIER)
                ?.takeIf { it.text in names }
                ?: return
        emit(identifier.startOffset, Messages[MSG], false)
    }

    internal companion object {
        const val MSG = "identifier.name.blacklisting"

        val NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_blacklist_identifier_names",
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
