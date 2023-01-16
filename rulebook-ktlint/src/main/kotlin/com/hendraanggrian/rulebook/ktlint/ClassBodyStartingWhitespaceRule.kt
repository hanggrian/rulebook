package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.CLASS_BODY
import com.pinterest.ktlint.core.ast.ElementType.CLASS_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.INTERFACE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.OBJECT_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.WHITE_SPACE
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#class-body-starting-whitespace).
 */
class ClassBodyStartingWhitespaceRule : Rule("class-body-starting-whitespace") {
    internal companion object {
        const val ERROR_MESSAGE = "%s empty line at the start of '%s'."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != CLASS_BODY) {
            return
        }

        // iterate through class declaration to find class keyword
        val `class` = node.treeParent // can be CLASS or OBJECT_DECLARATION
        val classKeyword = `class`.children().first {
            it.elementType == CLASS_KEYWORD ||
                it.elementType == INTERFACE_KEYWORD ||
                it.elementType == OBJECT_KEYWORD
        }

        // skip if class body is single line (e.g.: `class MyClass { val property = "" }`)
        val lbrace = node.firstChildNode
        val whitespace = lbrace.treeNext
        if (whitespace.elementType != WHITE_SPACE) {
            return
        }

        // determine single or multiple-line class declaration
        val classDeclaration = classKeyword.siblingsUntil(CLASS_BODY).joinToString("") { it.text }
        when {
            // single, report error if first line is empty line
            "\n" !in classDeclaration -> if ("\n\n" in whitespace.text) {
                emit(
                    whitespace.endOffset,
                    ERROR_MESSAGE.format("Unexpected", classKeyword.text),
                    false
                )
            }
            // multiple, report error if first line is not empty line
            else -> if ("\n\n" !in whitespace.text) {
                emit(
                    whitespace.endOffset,
                    ERROR_MESSAGE.format("Missing", classKeyword.text),
                    false
                )
            }
        }
    }
}
