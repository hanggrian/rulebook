package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.ec4j.core.model.PropertyType.PropertyValueParser.POSITIVE_INT_VALUE_PARSER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#reduce-file-length).
 */
public class ReduceFileLengthRule : RulebookRule(
    "reduce-file-length",
    setOf(FILE_LENGTH_PROPERTY),
) {
    private var maxLength = FILE_LENGTH_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        maxLength = editorConfig[FILE_LENGTH_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != FILE) {
            return
        }

        // checks for violation
        node.text.split('\n')
            .let { if (it.last().isEmpty()) it.lastIndex else it.size }
            .takeIf { it > maxLength }
            ?: return
        emit(node.startOffset, Messages.get(MSG, maxLength), false)
    }

    internal companion object {
        const val MSG = "reduce.file.length"

        val FILE_LENGTH_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "ktlint_rulebook_max_file_length",
                        "Max lines of code that is allowed.",
                        POSITIVE_INT_VALUE_PARSER,
                    ),
                defaultValue = 1000,
            )
    }
}
