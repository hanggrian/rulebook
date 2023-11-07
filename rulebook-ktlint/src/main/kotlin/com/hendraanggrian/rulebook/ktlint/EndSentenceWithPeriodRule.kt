package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.endOffset
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#end-sentence-with-period).
 */
class EndSentenceWithPeriodRule : RulebookRule("end-sentence-with-period") {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        // First line of filter.
        when (node.elementType) {
            KDOC_TAG -> {
                // Long descriptions have multiple lines, take only the last one.
                val kdocText =
                    node.children()
                        .findLast { it.elementType == KDOC_TEXT && it.text.isNotBlank() }
                        ?: return

                // Only enforce certain tags.
                val kdocTagName = node.findChildByType(KDOC_TAG_NAME) ?: return
                if (kdocTagName.text !in TARGET_TAGS) {
                    return
                }

                // Checks for violation after trimming optional comment.
                val punctuation = kdocText.text.trimComment().lastOrNull() ?: return
                if (punctuation !in END_PUNCTUATIONS) {
                    emit(kdocText.endOffset, Messages[MSG_TAG], false)
                }
            }
            EOL_COMMENT -> {
                // Long descriptions have multiple lines, take only the last one.
                if (node.treeNext?.elementType == WHITE_SPACE &&
                    node.treeNext?.treeNext?.elementType == EOL_COMMENT
                ) {
                    return
                }

                // Skip no description.
                if (node.text == "//") {
                    return
                }

                // Checks for violation.
                val punctuation = node.text.lastOrNull() ?: return
                if (punctuation !in END_PUNCTUATIONS) {
                    emit(node.endOffset, Messages[MSG_COMMENT], false)
                }
            }
        }
    }

    internal companion object {
        const val MSG_TAG = "end.sentence.with.period.tag"
        const val MSG_COMMENT = "end.sentence.with.period.comment"

        private val TARGET_TAGS = setOf("@param", "@return")
        private val END_PUNCTUATIONS = setOf('.', ')')

        private fun String.trimComment(): String {
            val index = indexOf("//")
            if (index == -1) {
                return this
            }
            return substring(0, index).trimEnd()
        }
    }
}
