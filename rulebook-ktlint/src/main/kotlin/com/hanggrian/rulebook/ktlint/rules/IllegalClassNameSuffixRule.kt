package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-name-suffix) */
public class IllegalClassNameSuffixRule : RulebookRule(ID, ILLEGAL_CLASS_FINAL_NAMES_PROPERTY) {
    private var illegalClassNameSuffixes = ILLEGAL_CLASS_FINAL_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION, FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        illegalClassNameSuffixes = editorConfig[ILLEGAL_CLASS_FINAL_NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain corresponding full and final name
        val (node2, fullName) =
            when (node.elementType) {
                CLASS, OBJECT_DECLARATION -> {
                    val identifier =
                        node
                            .findChildByType(IDENTIFIER)
                            ?: return
                    identifier to identifier.text
                }

                else -> node to (getFileName(node) ?: return)
            }

        // checks for violation
        val finalName =
            illegalClassNameSuffixes
                .singleOrNull { fullName.endsWith(it) }
                ?: return
        if (finalName in UTILITY_FINAL_NAMES) {
            emit(
                node2.startOffset,
                Messages.get(MSG_UTIL, fullName.substringBefore(finalName) + 's'),
                false,
            )
            return
        }
        emit(node2.startOffset, Messages.get(MSG_ALL, finalName), false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:illegal-class-name-suffix")
        public val ILLEGAL_CLASS_FINAL_NAMES_PROPERTY: EditorConfigProperty<Set<String>> =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_illegal_class_name_suffixes",
                        "A set of banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
                propertyWriter = { it.joinToString() },
            )

        private const val MSG_ALL = "illegal.class.name.suffix.all"
        private const val MSG_UTIL = "illegal.class.name.suffix.util"

        private val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }
}
