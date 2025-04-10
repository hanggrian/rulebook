package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation) */
public class BlockTagPunctuationRule : RulebookRule(ID, PUNCTUATE_BLOCK_TAGS_PROPERTY) {
    private var punctuateBlockTags = PUNCTUATE_BLOCK_TAGS_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(KDOC_TAG)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        punctuateBlockTags = editorConfig[PUNCTUATE_BLOCK_TAGS_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // only enforce certain tags
        val kdocTagName =
            node
                .findChildByType(KDOC_TAG_NAME)
                ?.takeIf { it.text in punctuateBlockTags }
                ?: return

        // long descriptions have multiple lines, take only the last one
        val kdocText =
            node
                .lastChildNode
                ?.takeIf { it.elementType == KDOC_TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation
        kdocText
            .takeUnless { it.text.lastOrNull() in END_PUNCTUATIONS }
            ?: return
        emit(kdocText.endOffset, Messages.get(MSG, kdocTagName.text), false)
    }

    public companion object {
        public val ID: RuleId = RuleId("${RulebookRuleSet.ID.value}:block-tag-punctuation")
        public val PUNCTUATE_BLOCK_TAGS_PROPERTY: EditorConfigProperty<Set<String>> =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_punctuate_block_tags",
                        "Block tags that have to end with a period.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue =
                    setOf(
                        "@constructor",
                        "@receiver",
                        "@property",
                        "@param",
                        "@return",
                    ),
                propertyWriter = { it.joinToString() },
            )

        private const val MSG = "block.tag.punctuation"

        private val END_PUNCTUATIONS = setOf('.', '!', '?', ')')
    }
}
