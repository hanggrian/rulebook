package com.hendraanggrian.rulebook.ktlint.kdoc

import com.hendraanggrian.rulebook.ktlint.RulebookRule
import com.hendraanggrian.rulebook.ktlint.contains
import com.hendraanggrian.rulebook.ktlint.endOffset
import com.hendraanggrian.rulebook.ktlint.get
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#tag-description-sentence).
 */
class TagDescriptionSentenceRule : RulebookRule("tag-description-sentence") {
    internal companion object {
        const val ERROR_MESSAGE = "Description of tag '%s' is not a sentence."
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
        if (kdocTagName.text != "@param" && kdocTagName.text != "@return" &&
            kdocTagName.text != "@throws" && kdocTagName.text != "@property" &&
            kdocTagName.text != "@receiver" && kdocTagName.text != "@constructor"
        ) {
            return
        }

        // check the suffix
        val tagDescription = node.text.trimComment().trimEnd()
        if (!tagDescription.endsWith('.') && !tagDescription.endsWith('?') &&
            !tagDescription.endsWith('!')
        ) {
            emit(kdocTagName.endOffset, ERROR_MESSAGE.format(kdocTagName.text), false)
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
