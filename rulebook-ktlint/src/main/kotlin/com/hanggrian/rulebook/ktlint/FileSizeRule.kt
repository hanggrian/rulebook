package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.ec4j.core.model.PropertyType.PropertyValueParser.POSITIVE_INT_VALUE_PARSER
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#file-size) */
public class FileSizeRule : RulebookRule(ID, LIMIT_FILE_SIZE_PROPERTY) {
    private var limitFileSize = LIMIT_FILE_SIZE_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        limitFileSize = editorConfig[LIMIT_FILE_SIZE_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .text
            .lines()
            .takeIf { (if (it.last().isEmpty()) it.lastIndex else it.size) > limitFileSize }
            ?: return
        emit(node.startOffset, Messages.get(MSG, limitFileSize), false)
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:file-size")
        val LIMIT_FILE_SIZE_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_max_file_size",
                        "Max lines of code that is allowed.",
                        POSITIVE_INT_VALUE_PARSER,
                    ),
                defaultValue = 1000,
            )

        const val MSG = "file.size"
    }
}
