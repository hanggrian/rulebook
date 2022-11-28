package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.contains
import com.hendraanggrian.lints.ktlint.endOffset
import com.hendraanggrian.lints.ktlint.get
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_MARKDOWN_LINK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TEXT
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/lints/blob/main/rules.md#tag-description-sentence).
 */
class TagDescriptionSentenceRule : Rule("tag-description-sentence") {
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

        // skips no description
        if (KDOC_TEXT !in node) {
            return
        }

        // only enforce certain tags
        val tagName = node.children().first().text
        if (tagName != "@param" && tagName != "@return" &&
            tagName != "@constructor" && tagName != "@property" && tagName != "@receiver"
        ) {
            return
        }

        // check the suffix
        val tagDescription = node.text.trimComment().trimEnd()
        if (!tagDescription.endsWith('.') && !tagDescription.endsWith('?') &&
            !tagDescription.endsWith('!')
        ) {
            emit(node[KDOC_MARKDOWN_LINK].endOffset, ERROR_MESSAGE.format(tagName), false)
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
