package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.properties.MAX_FILE_SIZE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.FILE
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#file-size) */
public class FileSizeRule : RulebookRule(ID, MAX_FILE_SIZE_PROPERTY) {
    private var maxFileSize = MAX_FILE_SIZE_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(FILE)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        maxFileSize = editorConfig[MAX_FILE_SIZE_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // checks for violation
        node
            .text
            .lines()
            .takeIf { (if (it.last().isEmpty()) it.lastIndex else it.size) > maxFileSize }
            ?: return
        emit(node.startOffset, Messages[MSG, maxFileSize], false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:file-size")
        private const val MSG = "file.size"
    }
}
