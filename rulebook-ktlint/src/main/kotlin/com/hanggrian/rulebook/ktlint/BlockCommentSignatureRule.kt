package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.contains
import com.hanggrian.rulebook.ktlint.internals.isMultiline
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_MARKDOWN_INLINE_LINK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_SECTION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.IndentConfig
import com.pinterest.ktlint.rule.engine.core.api.Rule.VisitorModifier.RunAfterRule
import com.pinterest.ktlint.rule.engine.core.api.Rule.VisitorModifier.RunAfterRule.Mode.REGARDLESS_WHETHER_RUN_AFTER_RULE_IS_LOADED_OR_DISABLED
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_SIZE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_STYLE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.MAX_LINE_LENGTH_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.isRoot
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-comment-signature) */
public class BlockCommentSignatureRule :
    RulebookRule(
        ID,
        setOf(INDENT_SIZE_PROPERTY, INDENT_STYLE_PROPERTY, MAX_LINE_LENGTH_PROPERTY),
        setOf(
            RunAfterRule(
                BlockCommentSpacingRule.ID,
                REGARDLESS_WHETHER_RUN_AFTER_RULE_IS_LOADED_OR_DISABLED,
            ),
        ),
    ) {
    private var indentConfig = IndentConfig.DEFAULT_INDENT_CONFIG
    private var maxLineLength = MAX_LINE_LENGTH_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(KDOC)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        indentConfig =
            IndentConfig(
                indentStyle = editorConfig[INDENT_STYLE_PROPERTY],
                tabWidth = editorConfig[INDENT_SIZE_PROPERTY],
            )
        maxLineLength = editorConfig[MAX_LINE_LENGTH_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // skip multiline comment content
        val children =
            node
                .children()
                .singleOrNull { it.elementType == KDOC_SECTION }
                ?.takeUnless { KDOC_TAG in it || WHITE_SPACE in it }
                ?.children()
                ?.filter {
                    it.elementType == KDOC_TEXT ||
                        it.elementType == KDOC_MARKDOWN_INLINE_LINK
                } ?: return

        // allow empty content
        val text =
            children
                .joinToString("") { it.text }
                .takeIf { it.isNotBlank() }
                ?: return

        // checks for violation
        val textLength = node.indentLength + text.length
        if (node.isMultiline()) {
            textLength
                .takeIf { it + SINGLELINE_TEMPLATE.length <= maxLineLength }
                ?: return
            emit(children.first().startOffset, Messages[MSG_JOIN], false)
            return
        }
        textLength
            .takeIf { it + SINGLELINE_TEMPLATE.length > maxLineLength + 1 }
            ?: return
        emit(children.first().startOffset, Messages[MSG_SPLIT], false)
    }

    private val ASTNode.indentLength: Int
        get() {
            var result = 0
            var current = this
            while (!current.isRoot()) {
                current = current.treeParent
                if (current.elementType == CLASS_BODY) {
                    result++
                }
            }
            return result * indentConfig.indent.length
        }

    internal companion object {
        val ID = RuleId("${RulebookRuleSet.ID.value}:block-comment-signature")

        const val MSG_JOIN = "block.comment.signature.join"
        const val MSG_SPLIT = "block.comment.signature.split"

        private const val SINGLELINE_TEMPLATE = "/** */"
    }
}
