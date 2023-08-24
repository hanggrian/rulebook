package com.hendraanggrian.rulebook.ktlint.docs

import com.hendraanggrian.rulebook.ktlint.Messages
import com.hendraanggrian.rulebook.ktlint.RulebookRule
import com.hendraanggrian.rulebook.ktlint.contains
import com.hendraanggrian.rulebook.ktlint.endOffset
import com.hendraanggrian.rulebook.ktlint.getOrNull
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/PunctuateTag).
 */
class PunctuateTagRule : RulebookRule("punctuate-tag") {
    internal companion object {
        const val MSG = "punctuate.tag"

        val DESCRIBABLE_TAGS =
            setOf("@param", "@return", "@throws", "@property", "@receiver", "@constructor")
        val END_PUNCTUATIONS = setOf('.', '?', '!')
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
        val kdocTagName = node.getOrNull(KDOC_TAG_NAME) ?: return
        if (kdocTagName.text !in DESCRIBABLE_TAGS) {
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
