package com.hendraanggrian.codestyle.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.KDOC_LEADING_ASTERISK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_MARKDOWN_INLINE_LINK
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TAG_NAME
import com.pinterest.ktlint.core.ast.ElementType.KDOC_TEXT
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * Rules regarding kdoc.
 *
 * ## Content
 * Main content of kdoc uses markdown style and formatting. A first word of every new markdown line
 * cannot be a `link` or `code`. However, a fenced code block is allowed.
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
            val kdocText = node.nextKdocTextSiblingUntil(KDOC_LEADING_ASTERISK) ?: return
            val text = kdocText.text.trimStart()
            if (text.startsWith('`') && !text.startsWith("```")) {
                // for first word code, simply check if `KDOC_TEXT` starts with backtick
                emit(node.treeNext.startOffset, ERROR_MESSAGE_CONTENT.format("code"), false)
            } else if (kdocText.treePrev.elementType == KDOC_MARKDOWN_INLINE_LINK) {
                // for first word link, check if previous sibling is `KDOC_MARKDOWN_LINK`
                emit(node.treeNext.startOffset, ERROR_MESSAGE_CONTENT.format("link"), false)
            }
        } else if (node.elementType == KDOC_TAG) {
            val tagName = node.findChildByType(KDOC_TAG_NAME)!!.text
            if (tagName != "@param" && tagName != "@property" && tagName != "@constructor" &&
                tagName != "@receiver" && tagName != "@return"
            ) {
                return
            }
            val kdocText = if (node.lastChildNode.isValidKdocText()) {
                node.lastChildNode
            } else {
                node.lastChildNode.prevKdocTextSibling() ?: return
            }
            val text = kdocText.text.trimEnd()
            if (!text.endsWith('.') && !text.endsWith('?') && !text.endsWith('!')) {
                // simply check if `KDOC_TEXT` ends with `.`, `?` or `!`.
                emit(kdocText.startOffset, ERROR_MESSAGE_TAG.format(tagName), false)
            }
        }
    }

    private fun ASTNode.nextKdocTextSiblingUntil(finalNodeType: IElementType): ASTNode? {
        var node = treeNext
        while (node != null && node.elementType != finalNodeType /* reached end */) {
            if (node.isValidKdocText()) {
                return node
            }
            node = node.treeNext
        }
        return null
    }

    private fun ASTNode.prevKdocTextSibling(): ASTNode? {
        var node = treePrev
        while (node != null) {
            if (node.isValidKdocText()) {
                return node
            }
            node = node.treePrev
        }
        return null
    }

    private fun ASTNode.isValidKdocText(): Boolean = elementType == KDOC_TEXT && text.isNotBlank()
}
