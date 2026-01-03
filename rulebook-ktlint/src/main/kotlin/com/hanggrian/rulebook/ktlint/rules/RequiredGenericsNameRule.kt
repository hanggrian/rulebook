package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FUN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_PARAMETER_LIST
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#required-generics-name) */
public class RequiredGenericsNameRule : RulebookRule(ID, REQUIRED_GENERIC_NAMES_PROPERTY) {
    private var requiredGenericNames = REQUIRED_GENERIC_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, FUN)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        requiredGenericNames = editorConfig[REQUIRED_GENERIC_NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // filter out multiple generics
        val typeParameter =
            node
                .findChildByType(TYPE_PARAMETER_LIST)
                ?.children20
                ?.singleOrNull { it.elementType == TYPE_PARAMETER }
                ?: return

        // checks for violation
        val identifier =
            typeParameter
                .findChildByType(IDENTIFIER)
                ?.takeUnless { node.hasParentWithGenerics() || it.text in requiredGenericNames }
                ?: return
        emit(identifier.startOffset, Messages[MSG, requiredGenericNames.joinToString()], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:required-generics-name")
        public val REQUIRED_GENERIC_NAMES_PROPERTY: EditorConfigProperty<Set<String>> =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_required_generics_names",
                        "A set of common generics.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("E", "K", "N", "T", "V"),
                propertyWriter = { it.joinToString() },
            )

        private const val MSG = "required.generics.name"

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
