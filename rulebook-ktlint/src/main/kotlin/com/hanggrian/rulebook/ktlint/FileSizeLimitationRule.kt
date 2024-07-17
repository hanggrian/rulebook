package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.ec4j.core.model.PropertyType.PropertyValueParser.POSITIVE_INT_VALUE_PARSER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#file-size-limitation)
 */
public class FileSizeLimitationRule :
    Rule(
        "file-size-limitation",
        setOf(MAX_FILE_LENGTH_PROPERTY),
    ) {
    private var maxFileLength = MAX_FILE_LENGTH_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        maxFileLength = editorConfig[MAX_FILE_LENGTH_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
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
