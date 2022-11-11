package com.hendraanggrian.lints.ktlint.kdoc

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See Guide](https://github.com/hendraanggrian/lints/blob/main/guides/docs/paragraph-continuation-first-word.md).
 */
class ParagraphContinuationFirstWordRule : Rule("paragraph-continuation-first-word") {
    internal companion object {
        const val ERROR_MESSAGE = "First word of paragraph continuation cannot be a %s."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        if (node.elementType != KDOC_LEADING_ASTERISK) {
            return
        }
        // skips first line of paragraph
        if (node.treePrev == null || node.treePrev.treePrev == null) {
            return
        }
        // while loop until line ends or has tags
        var next = node.treeNext
        val sb = StringBuilder()
        while (next != null) {
            when (next.elementType) {
                KDOC_LEADING_ASTERISK -> break
                KDOC_TAG -> return
            }
            sb.append(next.text)
            next = next.treeNext
        }
        // check the first word of paragraph continuation
        val line = sb.trimStart()
        if (line.startsWith('`') && !line.startsWith("```")) {
            emit(node.treeNext.startOffset, ERROR_MESSAGE.format("code"), false)
        } else if (line.startsWith('[')) {
            emit(node.treeNext.startOffset, ERROR_MESSAGE.format("link"), false)
        }
    }
}
