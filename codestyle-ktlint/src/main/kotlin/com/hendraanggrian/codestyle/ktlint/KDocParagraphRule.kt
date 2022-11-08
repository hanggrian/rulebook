package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * **Guide**: [Documentation Paragraph](https://github.com/hendraanggrian/codestyle/blob/main/guides/documentation-paragraph.md).
 */
class KDocParagraphRule : Rule("documentation-content") {
    internal companion object {
        const val ERROR_MESSAGE = "First word of a line cannot be a %s."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        if (node.elementType != KDOC_LEADING_ASTERISK) {
            return
        }
        // skip tags
        var next = node.treeNext
        while (next != null) {
            when (next.elementType) {
                KDOC_LEADING_ASTERISK -> break
                KDOC_TAG -> return
            }
            next = next.treeNext
        }
        // check the first word of paragraph continuation
        val line = node.lineAfterAsterisk.trimStart()
        if (line.startsWith('`') && !line.startsWith("```")) {
            emit(node.treeNext.startOffset, ERROR_MESSAGE.format("code"), false)
        } else if (line.startsWith('[')) {
            emit(node.treeNext.startOffset, ERROR_MESSAGE.format("link"), false)
        }
    }

    /** While loop until next leading asterisk. */
    private val ASTNode.lineAfterAsterisk: String
        get() {
            var next = treeNext
            val sb = StringBuilder()
            while (next != null &&
                next.elementType != KDOC_LEADING_ASTERISK /* reached new line */
            ) {
                sb.append(next.text)
                next = next.treeNext
            }
            return sb.toString()
        }
}
