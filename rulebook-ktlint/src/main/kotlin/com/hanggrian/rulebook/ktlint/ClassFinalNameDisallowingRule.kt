package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-final-name-disallowing)
 */
public class ClassFinalNameDisallowingRule :
    Rule(
        "class-final-name-disallowing",
        setOf(FINAL_NAMES_PROPERTY),
    ) {
    private var names = FINAL_NAMES_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(CLASS, OBJECT_DECLARATION, FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[FINAL_NAMES_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // obtain corresponding full and final name
        val (node2, fullName) =
            when (node.elementType) {
                CLASS, OBJECT_DECLARATION -> node.findChildByType(IDENTIFIER)?.let { it to it.text }
                else -> getFileName(node)?.let { node to it }
            } ?: return

        // checks for violation
        val finalName =
            names
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
        const val MSG_ALL = "class.final.name.disallowing.all"
        const val MSG_UTIL = "class.final.name.disallowing.util"

        private val UTILITY_FINAL_NAMES = setOf("Util", "Utility")

        val FINAL_NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_disallowed_class_final_names",
                        "A set of banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
                propertyWriter = { it.joinToString() },
            )
    }
}
