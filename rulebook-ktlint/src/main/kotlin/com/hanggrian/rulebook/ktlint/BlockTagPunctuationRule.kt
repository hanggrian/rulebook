package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.internals.Messages
import com.hanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#block-tag-punctuation)
 */
public class BlockTagPunctuationRule :
    Rule(
        "block-tag-punctuation",
        setOf(PUNCTUATED_BLOCK_TAGS_PROPERTY),
    ) {
    private var punctuatedTags = PUNCTUATED_BLOCK_TAGS_PROPERTY.defaultValue

    override val tokens: TokenSet = TokenSet.create(KDOC_TAG)

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        punctuatedTags = editorConfig[PUNCTUATED_BLOCK_TAGS_PROPERTY]
    }

    override fun visitToken(node: ASTNode, emit: Emit) {
        // only enforce certain tags
        node
            .findChildByType(KDOC_TAG_NAME)
            ?.takeIf { it.text in punctuatedTags }
            ?: return

        // long descriptions have multiple lines, take only the last one
        val kdocText =
            node
                .lastChildNode
                ?.takeIf { it.elementType == KDOC_TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation
        kdocText.text
            .trimComment()
            .lastOrNull()
            ?.takeUnless { it in END_PUNCTUATIONS }
            ?: return
        emit(kdocText.endOffset, Messages.get(MSG, punctuatedTags.joinToString()), false)
    }

    internal companion object {
        const val MSG = "block.tag.punctuation"

        private val END_PUNCTUATIONS = setOf('.', ')')

        val PUNCTUATED_BLOCK_TAGS_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_punctuated_block_tags",
                        "Block tags that have to end with a period.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("@param", "@return"),
                propertyWriter = { it.joinToString() },
            )

        private fun String.trimComment(): String =
            indexOf("//")
                .takeUnless { it == -1 }
                ?.let { substring(0, it).trimEnd() }
                ?: this
    }
}
