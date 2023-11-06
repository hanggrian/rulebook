package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.contains
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-tag-with-period).
 */
class EndTagWithPeriodRule : RulebookRule("end-tag-with-period") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
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
        val kdocTagName = node.findChildByType(KDOC_TAG_NAME) ?: return
        if (kdocTagName.text !in DESCRIBABLE_TAGS) {
            return
        }

        // check the suffix
        val punctuation = node.text.trimComment().trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            emit(kdocTagName.endOffset, Messages[MSG], false)
        }
    }

    internal companion object {
        const val MSG = "end.tag.with.period"

        private val DESCRIBABLE_TAGS =
            setOf("@param", "@return", "@throws", "@property", "@receiver", "@constructor")
        private val END_PUNCTUATIONS = setOf('.', '?', '!')

        private fun String.trimComment(): String {
            val index = indexOf("//")
            if (index == -1) {
                return this
            }
            return substring(0, index).trimEnd()
        }
    }
}
