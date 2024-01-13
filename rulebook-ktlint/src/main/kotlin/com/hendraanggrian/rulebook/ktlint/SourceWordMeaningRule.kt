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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning).
 */
public class SourceWordMeaningRule : RulebookRule(
    "source-word-meaning",
    setOf(MEANINGLESS_WORDS_PROPERTY, MEANINGLESS_WORDS_IGNORED_PROPERTY),
) {
    private var meaninglessWords = MEANINGLESS_WORDS_PROPERTY.defaultValue
    private var ignoredWords = MEANINGLESS_WORDS_IGNORED_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        meaninglessWords = editorConfig[MEANINGLESS_WORDS_PROPERTY]
        ignoredWords = editorConfig[MEANINGLESS_WORDS_IGNORED_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != FILE &&
            node.elementType != CLASS &&
            node.elementType != OBJECT_DECLARATION
        ) {
            return
        }

        // get file or identifier name
        val (name, ast) =
            when (node.elementType) {
                FILE -> (getFileName(node) ?: return) to node
                else -> node.findChildByType(IDENTIFIER)?.let { it.text to it } ?: return
            }

        // checks for violation
        TITLE_CASE_REGEX.findAll(name)
            .filter { it.value in meaninglessWords && it.value !in ignoredWords }
            .forEach { emit(ast.startOffset, Messages.get(MSG, it.value), false) }
    }

    internal companion object {
        const val MSG = "source.word.meaning"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")

        val MEANINGLESS_WORDS_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_meaningless_words",
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

        val MEANINGLESS_WORDS_IGNORED_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_meaningless_words_ignored",
                        "Exclusion to the banned words.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = emptySet(),
                propertyWriter = { it.joinToString() },
            )
    }
}
