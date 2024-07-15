package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.getFileName
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_DECLARATION
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-final-name-blacklisting)
 */
public class ClassFinalNameBlacklistingRule :
    Rule(
        "class-final-name-blacklisting",
        setOf(FINAL_NAMES_PROPERTY),
    ),
    RuleAutocorrectApproveHandler {
    private var names = FINAL_NAMES_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        names = editorConfig[FINAL_NAMES_PROPERTY]
    }

    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != CLASS &&
            node.elementType != OBJECT_DECLARATION &&
            node.elementType != FILE
        ) {
            return
        }

        // obtain corresponding full and final name
        val (node2, fullName, finalName) =
            when (node.elementType) {
                CLASS, OBJECT_DECLARATION -> {
                    val identifier = node.findChildByType(IDENTIFIER) ?: return
                    val finalName = names.firstOrNull { identifier.text.endsWith(it) } ?: return
                    Triple(identifier, identifier.text, finalName)
                }
                else -> {
                    val fileName = getFileName(node) ?: return
                    val finalName = names.firstOrNull { fileName.endsWith(it) } ?: return
                    Triple(node, fileName, finalName)
                }
            }

        // checks for violation
        when (finalName) {
            "Util", "Utility" ->
                emit(
                    node2.startOffset,
                    Messages.get(MSG_UTIL, fullName.substringBefore(finalName) + 's'),
                    false,
                )
            else -> emit(node2.startOffset, Messages.get(MSG_ALL, finalName), false)
        }
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
