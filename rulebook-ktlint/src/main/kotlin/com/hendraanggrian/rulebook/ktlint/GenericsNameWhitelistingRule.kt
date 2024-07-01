package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Emit
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-name-whitelisting)
 */
public class GenericsNameWhitelistingRule :
    Rule(
        "generics-name-whitelisting",
        setOf(NAMES_PROPERTY),
    ),
    RuleAutocorrectApproveHandler {
    private var names = NAMES_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[NAMES_PROPERTY]
    }

    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS && node.elementType != FUN) {
            return
        }

        // filter out multiple generics
        val typeParameter =
            node
                .findChildByType(TYPE_PARAMETER_LIST)
                ?.children()
                ?.singleOrNull { it.elementType == TYPE_PARAMETER }
                ?: return

        // checks for violation
        val identifier =
            typeParameter
                .findChildByType(IDENTIFIER)
                ?.takeUnless { node.hasParentWithGenerics() || it.text in names }
                ?: return
        emit(identifier.startOffset, Messages.get(MSG, names.joinToString()), false)
    }

    internal companion object {
        const val MSG = "generics.name.whitelisting"

        val NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_whitelist_generics_names",
                        "A set of common generics.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("E", "K", "N", "T", "V"),
                propertyWriter = { it.joinToString() },
            )

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            var next = treeParent
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
