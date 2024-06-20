package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#class-final-name-blacklisting)
 */
public class ClassFinalNameBlacklistingRule : Rule(
    "class-final-name-blacklisting",
    setOf(FINAL_NAMES_PROPERTY),
) {
    private var names = FINAL_NAMES_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[FINAL_NAMES_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            CLASS, OBJECT_DECLARATION -> {
                // checks for violation
                val identifier = node.findChildByType(IDENTIFIER) ?: return
                val finalName = names.firstOrNull { identifier.text.endsWith(it) } ?: return
                process(identifier, identifier.text, finalName, emit)
            }
            FILE -> {
                // checks for violation
                val fileName = getFileName(node) ?: return
                val finalName = names.firstOrNull { fileName.endsWith(it) } ?: return
                process(node, fileName, finalName, emit)
            }
        }
    }

    private fun process(
        node: ASTNode,
        fullName: String,
        finalName: String,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ): Unit =
        when (finalName) {
            "Util", "Utility" ->
                emit(
                    node.startOffset,
                    Messages.get(MSG_UTIL, fullName.substringBefore(finalName) + 's'),
                    false,
                )
            else -> emit(node.startOffset, Messages.get(MSG_ALL, finalName), false)
        }

    internal companion object {
        const val MSG_ALL = "class.final.name.blacklisting.all"
        const val MSG_UTIL = "class.final.name.blacklisting.util"

        val FINAL_NAMES_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_blacklist_class_final_names",
                        "A set of banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
                propertyWriter = { it.joinToString() },
            )
    }
}
