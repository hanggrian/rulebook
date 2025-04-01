package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.getFileName
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

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-final-name) */
public class IllegalClassFinalNameRule : RulebookRule(ID, DISALLOW_CLASS_FINAL_NAMES_PROPERTY) {
    private var disallowClassFinalNames = DISALLOW_CLASS_FINAL_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION, FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        disallowClassFinalNames = editorConfig[DISALLOW_CLASS_FINAL_NAMES_PROPERTY]
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
            disallowClassFinalNames
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

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:illegal-class-final-name")
        val DISALLOW_CLASS_FINAL_NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_illegal_class_final_names",
                        "A set of banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
                propertyWriter = { it.joinToString() },
            )

        const val MSG_ALL = "illegal.class.final.name.all"
        const val MSG_UTIL = "illegal.class.final.name.util"

        private val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }
}
