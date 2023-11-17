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
import org.ec4j.core.model.PropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#avoid-meaningless-word).
 */
public class AvoidMeaninglessWordRule : RulebookRule(
    "avoid-meaningless-word",
    setOf(MEANINGLESS_WORDS_PROPERTY, MEANINGLESS_WORDS_EXCLUDE_PROPERTY),
) {
    private var words = MEANINGLESS_WORDS_PROPERTY.defaultValue
    private var exclude = MEANINGLESS_WORDS_EXCLUDE_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        words = editorConfig[MEANINGLESS_WORDS_PROPERTY]
        exclude = editorConfig[MEANINGLESS_WORDS_EXCLUDE_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            CLASS, OBJECT_DECLARATION -> {
                // retrieve name
                val identifier = node.findChildByType(IDENTIFIER) ?: return

                // checks for violation
                TITLE_CASE_REGEX.findAll(identifier.text)
                    .filter { it.value in words && it.value !in exclude }
                    .forEach { emit(identifier.startOffset, Messages.get(MSG, it.value), false) }
            }
            FILE -> {
                // retrieve name
                val fileName = getFileName(node) ?: return

                // checks for violation
                TITLE_CASE_REGEX.findAll(fileName)
                    .filter { it.value in words && it.value !in exclude }
                    .forEach { emit(node.startOffset, Messages.get(MSG, it.value), false) }
            }
        }
    }

    internal companion object {
        const val MSG = "avoid.meaningless.word"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")

        val MEANINGLESS_WORDS_PROPERTY =
            EditorConfigProperty(
                type =
                    PropertyType.LowerCasingPropertyType(
                        "ktlint_rulebook_meaningless_words",
                        "A set of banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue =
                    setOf(
                        "Abstract",
                        "Base",
                        "Util",
                        "Utility",
                        "Helper",
                        "Manager",
                        "Wrapper",
                        "Data",
                        "Info",
                    ),
                propertyWriter = { it.joinToString() },
            )

        val MEANINGLESS_WORDS_EXCLUDE_PROPERTY =
            EditorConfigProperty(
                type =
                    PropertyType.LowerCasingPropertyType(
                        "ktlint_rulebook_meaningless_words_exclude",
                        "Ignore specified words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = emptySet(),
                propertyWriter = { it.joinToString() },
            )
    }
}
