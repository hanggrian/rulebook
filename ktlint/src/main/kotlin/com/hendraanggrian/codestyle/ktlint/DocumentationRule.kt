package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_MARKDOWN_INLINE_LINK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * Rules regarding kdoc.
 *
 * ## Content
 * Main content of kdoc uses markdown style and formatting. A first word of every new markdown line
 * cannot be a `link` or `code`.
 *
 * ## [Tags](https://kotlinlang.org/docs/kotlin-doc.html)
 * Description of param, property, constructor, receiver and return tag is a sentence.
 * Therefore, must end with `.`, `?` or `!`.
 */
class DocumentationRule : Rule("documentation-rule") {
    internal companion object {
        const val ERROR_MESSAGE_CONTENT = "First word of a line cannot be a %s."
        const val ERROR_MESSAGE_TAG = "%s tag description is not a sentence."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (Int, String, Boolean) -> Unit
    ) {
        if (node.elementType == KDOC_LEADING_ASTERISK) {
            val kdocText = node.nextValidKdocText(KDOC_LEADING_ASTERISK) ?: return
            if (kdocText.text.trimStart().startsWith("`")) {
                // for first word code, simply check if `KDOC_TEXT` starts with backtick
                emit(node.treeNext.startOffset, ERROR_MESSAGE_CONTENT.format("code"), false)
            } else if (kdocText.treePrev.elementType == KDOC_MARKDOWN_INLINE_LINK) {
                // for first word link, check if previous sibling is `KDOC_MARKDOWN_LINK`
                emit(node.treeNext.startOffset, ERROR_MESSAGE_CONTENT.format("link"), false)
            }
        } else if (node.elementType == KDOC_TAG_NAME) {
            val tagName = when (node.text) {
                "@param" -> "Param"
                "@property" -> "Property"
                "@constructor" -> "Constructor"
                "@receiver" -> "Receiver"
                "@return" -> "Return"
                else -> return
            }
            val kdocText = node.nextValidKdocText(KDOC_TAG_NAME) ?: return
            if (!kdocText.text.endsWith('.') &&
                !kdocText.text.endsWith('?') &&
                !kdocText.text.endsWith('!')
            ) {
                // simply check if `KDOC_TEXT` ends with `.`, `?` or `!`.
                emit(kdocText.startOffset, ERROR_MESSAGE_TAG.format(tagName), false)
            }
        }
    }

    private fun ASTNode.nextValidKdocText(startingNodeType: IElementType): ASTNode? {
        var next = treeNext
        while (next != null) {
            when {
                // reached a new line, meaning that current line is empty
                next.elementType == startingNodeType -> return null
                // found a valid `KDOC_TEXT`
                next.elementType == KDOC_TEXT && next.text.isNotBlank() -> break
            }
            next = next.treeNext
        }
        return next
    }
}
