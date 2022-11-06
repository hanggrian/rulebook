package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * **Guide**: [Documentation Content](https://github.com/hendraanggrian/codestyle/blob/main/guides/documentation-content.md).
 */
class DocumentationContentRule : Rule("documentation-content") {
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
            var node = treeNext
            val sb = StringBuilder()
            while (node != null &&
                node.elementType != KDOC_LEADING_ASTERISK /* reached new line */
            ) {
                sb.append(node.text)
                node = node.treeNext
            }
            return sb.toString()
        }
}
