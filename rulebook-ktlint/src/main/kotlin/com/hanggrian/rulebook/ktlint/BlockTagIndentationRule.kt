package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.hanggrian.rulebook.ktlint.internals.siblingsUntil
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.rule.engine.core.api.IndentConfig
import com.pinterest.ktlint.rule.engine.core.api.IndentConfig.IndentStyle.TAB
import com.pinterest.ktlint.rule.engine.core.api.Rule.VisitorModifier.RunAfterRule
import com.pinterest.ktlint.rule.engine.core.api.Rule.VisitorModifier.RunAfterRule.Mode.REGARDLESS_WHETHER_RUN_AFTER_RULE_IS_LOADED_OR_DISABLED
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_SIZE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_STYLE_PROPERTY
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#block-tag-indentation) */
public class BlockTagIndentationRule :
    RulebookRule(
        ID,
        setOf(INDENT_SIZE_PROPERTY, INDENT_STYLE_PROPERTY),
        setOf(
            RunAfterRule(
                BlockCommentSpacesRule.ID,
                REGARDLESS_WHETHER_RUN_AFTER_RULE_IS_LOADED_OR_DISABLED,
            ),
        ),
    ) {
    private var indentConfig = IndentConfig.DEFAULT_INDENT_CONFIG

    override val tokens: TokenSet = TokenSet.create(KDOC_LEADING_ASTERISK)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        indentConfig =
            IndentConfig(
                indentStyle = editorConfig[INDENT_STYLE_PROPERTY],
                tabWidth = editorConfig[INDENT_SIZE_PROPERTY],
            )
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // target leading whitespace
        val text =
            node
                .siblingsUntil(KDOC_LEADING_ASTERISK)
                .joinToString("") { it.text }
                .takeIf { it.firstOrNull()?.isWhitespace() ?: false }
                ?.drop(1)
                ?: return

        // checks for violation
        text
            .takeIf {
                it.isNotEmpty() &&
                    it.first().isWhitespace() &&
                    !it.startsWith(indentConfig.indent)
            } ?: return
        emit(
            node.endOffset,
            Messages.get(
                MSG,
                when {
                    indentConfig.indentStyle == TAB -> "a tab"
                    indentConfig.tabWidth == 1 -> "a space"
                    else -> "${indentConfig.tabWidth} spaces"
                },
            ),
            false,
        )
    }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:block-tag-indentation")

        const val MSG = "block.tag.indentation"
    }
}
