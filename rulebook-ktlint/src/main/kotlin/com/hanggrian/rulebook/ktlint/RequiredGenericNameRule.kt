package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#required-generic-name) */
public class RequiredGenericNameRule : RulebookRule(ID, ALLOW_GENERICS_NAMES_PROPERTY) {
    private var allowGenericsNames = ALLOW_GENERICS_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, FUN)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        allowGenericsNames = editorConfig[ALLOW_GENERICS_NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
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
                ?.takeUnless { node.hasParentWithGenerics() || it.text in allowGenericsNames }
                ?: return
        emit(identifier.startOffset, Messages.get(MSG, allowGenericsNames.joinToString()), false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:required-generic-name")
        val ALLOW_GENERICS_NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_required_generic_names",
                        "A set of common generics.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("E", "K", "N", "T", "V"),
                propertyWriter = { it.joinToString() },
            )

        const val MSG = "required.generic.name"

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            var next = treeParent
            while (next != null) {
                next
                    .takeUnless { TYPE_PARAMETER_LIST in it }
                    ?: return true
                next = next.treeParent
            }
            return false
        }
    }
}
