package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.Messages
import com.hanggrian.rulebook.ktlint.RulebookRuleSet
import com.hanggrian.rulebook.ktlint.contains
import com.hanggrian.rulebook.ktlint.isMultiline
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
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_SIZE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.INDENT_STYLE_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.MAX_LINE_LENGTH_PROPERTY
import com.pinterest.ktlint.rule.engine.core.api.isRoot20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-comment-clip) */
public class BlockCommentClipRule :
    RulebookRule(
        ID,
        setOf(INDENT_SIZE_PROPERTY, INDENT_STYLE_PROPERTY, MAX_LINE_LENGTH_PROPERTY),
        setOf(
            RunAfterRule(
                BlockCommentSpacesRule.ID,
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
        // skip single-line or block tag
        val children =
            node
                .takeIf { it.isMultiline() }
                ?.children20
                ?.singleOrNull { it.elementType === KDOC_SECTION }
                ?.takeUnless { KDOC_TAG in it || WHITE_SPACE in it }
                ?.children20
                ?.filter {
                    it.elementType === KDOC_TEXT ||
                        it.elementType === KDOC_MARKDOWN_INLINE_LINK
                } ?: return

        // checks for violation
        val textLength =
            node.indentLength +
                children.joinToString("") { it.text }.length
        textLength
            .takeIf { it + SINGLELINE_TEMPLATE.length <= maxLineLength }
            ?: return
        emit(node.startOffset, Messages[MSG], false)
    }

    private val ASTNode.indentLength: Int
        get() {
            var result = 0
            var current = this
            while (!current.isRoot20) {
                current = current.treeParent
                if (current.elementType === CLASS_BODY) {
                    result++
                }
            }
            return result * indentConfig.indent.length
        }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-comment-clip")
        private const val MSG = "block.comment.clip"

        private const val SINGLELINE_TEMPLATE = "/** */"
    }
}
