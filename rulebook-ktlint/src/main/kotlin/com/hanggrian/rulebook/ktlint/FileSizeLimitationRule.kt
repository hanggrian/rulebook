package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Emit
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleAutocorrectApproveHandler
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.ec4j.core.model.PropertyType.PropertyValueParser.POSITIVE_INT_VALUE_PARSER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#file-size-limitation)
 */
public class FileSizeLimitationRule :
    Rule(
        "file-size-limitation",
        setOf(MAX_FILE_LENGTH_PROPERTY),
    ),
    RuleAutocorrectApproveHandler {
    private var maxFileLength = MAX_FILE_LENGTH_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        maxFileLength = editorConfig[MAX_FILE_LENGTH_PROPERTY]
    }

    override fun beforeVisitChildNodes(node: ASTNode, emit: Emit) {
        // first line of filter
        if (node.elementType != FILE) {
            return
        }

        // checks for violation
        node.text
            .lines()
            .takeIf { (if (it.last().isEmpty()) it.lastIndex else it.size) > maxFileLength }
            ?: return
        emit(node.startOffset, Messages.get(MSG, maxFileLength), false)
    }

    internal companion object {
        const val MSG = "file.size.limitation"

        val MAX_FILE_LENGTH_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_max_file_size",
                        "Max lines of code that is allowed.",
                        POSITIVE_INT_VALUE_PARSER,
                    ),
                defaultValue = 1000,
            )
    }
}
