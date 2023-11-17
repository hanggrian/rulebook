package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.CommaSeparatedListValueParser
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfig
import com.pinterest.ktlint.rule.engine.core.api.editorconfig.EditorConfigProperty
import org.ec4j.core.model.PropertyType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-block-tag-with-period).
 */
public class EndBlockTagWithPeriod : RulebookRule(
    "end-block-tag-with-period",
    setOf(BLOCK_TAGS_PROPERTY),
) {
    private var tags = BLOCK_TAGS_PROPERTY.defaultValue

    override fun beforeFirstNode(editorConfig: EditorConfig) {
        tags = editorConfig[BLOCK_TAGS_PROPERTY]
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // first line of filter
        when (node.elementType) {
            KDOC_TAG -> {
                // long descriptions have multiple lines, take only the last one
                val kdocText =
                    node.children()
                        .findLast { it.elementType == KDOC_TEXT && it.text.isNotBlank() }
                        ?: return

                // only enforce certain tags
                val kdocTagName = node.findChildByType(KDOC_TAG_NAME) ?: return
                if (kdocTagName.text !in tags) {
                    return
                }

                // checks for violation after trimming optional comment
                val punctuation = kdocText.text.trimComment().lastOrNull() ?: return
                if (punctuation !in END_PUNCTUATIONS) {
                    emit(kdocText.endOffset, Messages[MSG], false)
                }
            }
        }
    }

    internal companion object {
        const val MSG = "end.block.tag.with.period"

        private val END_PUNCTUATIONS = setOf('.', ')')

        val BLOCK_TAGS_PROPERTY =
            EditorConfigProperty(
                type =
                    PropertyType.LowerCasingPropertyType(
                        "ktlint_rulebook_block_tags",
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
