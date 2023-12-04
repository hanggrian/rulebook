package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType.LowerCasingPropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#block-tag-punctuation).
 */
public class BlockTagPunctuationRule : RulebookRule(
    "block-tag-punctuation",
    setOf(PUNCTUATED_TAGS_PROPERTY),
) {
    private var punctuatedTags = PUNCTUATED_TAGS_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        punctuatedTags = editorConfig[PUNCTUATED_TAGS_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        if (node.elementType != KDOC_TAG) {
            return
        }

        // only enforce certain tags
        node.findChildByType(KDOC_TAG_NAME)
            ?.takeUnless { it.text !in punctuatedTags }
            ?: return

        // long descriptions have multiple lines, take only the last one
        val kdocText =
            node.children()
                .findLast { it.elementType == KDOC_TEXT && it.text.isNotBlank() }
                ?: return

        // checks for violation after trimming optional comment
        kdocText.text.trimComment().lastOrNull()
            ?.takeIf { it !in END_PUNCTUATIONS }
            ?: return
        emit(kdocText.endOffset, Messages.get(MSG, punctuatedTags.joinToString()), false)
    }

    internal companion object {
        const val MSG = "block.tag.punctuation"

        private val END_PUNCTUATIONS = setOf('.', ')')

        val PUNCTUATED_TAGS_PROPERTY =
            EditorConfigProperty(
                type =
                    LowerCasingPropertyType(
                        "rulebook_punctuated_tags",
                        "Block tags that have to end with a period.",
                        CommaSeparatedListValueParser(),
                    ),
                defaultValue = setOf("@param", "@return"),
                propertyWriter = { it.joinToString() },
            )

        private fun String.trimComment(): String {
            val index = indexOf("//")
            if (index == -1) {
                return this
            }
            return substring(0, index).trimEnd()
        }
    }
}
