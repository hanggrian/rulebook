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
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning)
 */
public class SourceWordMeaningRule : RulebookRule(
    "source-word-meaning",
    setOf(MEANINGLESS_WORDS_PROPERTY),
) {
    private var meaninglessWords = MEANINGLESS_WORDS_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        meaninglessWords = editorConfig[MEANINGLESS_WORDS_PROPERTY]
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
                TITLE_CASE_REGEX.findAll(identifier.text)
                    .filter { it.value in meaninglessWords }
                    .forEach { process(identifier, identifier.text, it, emit) }
            }
            FILE -> {
                // checks for violation
                val fileName = getFileName(node) ?: return
                TITLE_CASE_REGEX.findAll(fileName)
                    .filter { it.value in meaninglessWords }
                    .forEach { process(node, fileName, it, emit) }
            }
        }
    }

    private fun process(
        node: ASTNode,
        fullName: String,
        result: MatchResult,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ): Unit =
        when (val word = result.value) {
            "Util", "Utility" ->
                emit(
                    node.startOffset,
                    Messages.get(MSG_UTIL, fullName.substringBefore(word) + 's'),
                    false,
                )
            else -> emit(node.startOffset, Messages.get(MSG_ALL, word), false)
        }

    internal companion object {
        const val MSG_ALL = "source.word.meaning.all"
        const val MSG_UTIL = "source.word.meaning.util"

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
                defaultValue = setOf("Util", "Utility", "Helper", "Manager", "Wrapper"),
                propertyWriter = { it.joinToString() },
            )
    }
}
