package com.hendraanggrian.rulebook.ktlint.docs

import com.hendraanggrian.rulebook.ktlint.Messages
import com.hendraanggrian.rulebook.ktlint.RulebookRule
import com.hendraanggrian.rulebook.ktlint.contains
import com.hendraanggrian.rulebook.ktlint.endOffset
import com.hendraanggrian.rulebook.ktlint.get
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/TagDescriptionPunctuation).
 */
class TagDescriptionPunctuationRule : RulebookRule("tag-description-punctuation") {
    internal companion object {
        const val MSG = "tag.description.punctuation"

        private val TAG_TARGETS =
            setOf("@param", "@return", "@throws", "@property", "@receiver", "@constructor")
        private val END_PUNCTUATIONS = setOf('.', '?', '!')
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != KDOC_TAG) {
            return
        }

        // skip no description
        if (KDOC_TEXT !in node) {
            return
        }

        // only enforce certain tags
        val kdocTagName = node[KDOC_TAG_NAME]
        if (kdocTagName.text !in TAG_TARGETS) {
            return
        }

        // check the suffix
        val punctuation = node.text.trimComment().trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            emit(kdocTagName.endOffset, Messages.get(MSG, kdocTagName.text), false)
        }
    }

    private fun String.trimComment(): String {
        val index = indexOf("//")
        if (index == -1) {
            return this
        }
        return substring(0, index).trimEnd()
    }
}
