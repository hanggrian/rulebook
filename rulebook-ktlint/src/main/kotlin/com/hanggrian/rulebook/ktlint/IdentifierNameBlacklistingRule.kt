package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.DESTRUCTURING_DECLARATION_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.VALUE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#identifier-name-blacklisting)
 */
public class IdentifierNameBlacklistingRule :
    Rule(
        "identifier-name-blacklisting",
        setOf(NAMES_PROPERTY),
    ) {
    private var names = NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet =
        TokenSet.create(
            PROPERTY,
            VALUE_PARAMETER,
            DESTRUCTURING_DECLARATION_ENTRY,
        )

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        val identifier =
            node
                .findChildByType(IDENTIFIER)
                ?.takeIf {
                    when {
                        !it.text.startsWith('`') || !it.text.endsWith('`') -> it.text
                        else -> it.text.substring(1, it.text.lastIndex)
                    } in names
                } ?: return
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
                        "any",
                        "boolean",
                        "byte",
                        "char",
                        "double",
                        "float",
                        "int",
                        "integer",
                        "long",
                        "short",
                        "array",
                        "string",
                        "list",
                        "set",
                        "map",
                        "collection",
                    ),
                propertyWriter = { it.joinToString() },
            )
    }
}
