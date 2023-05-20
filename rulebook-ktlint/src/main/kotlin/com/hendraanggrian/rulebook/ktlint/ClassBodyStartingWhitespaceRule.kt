package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERFACE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#class-body-starting-whitespace).
 */
class ClassBodyStartingWhitespaceRule : RulebookRule("class-body-starting-whitespace") {
    internal companion object {
        const val ERROR_MESSAGE = "%s first empty line."
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
        val `class` = node.treeParent // can be CLASS, OBJECT_DECLARATION
        val classKeyword = `class`.children().firstOrNull {
            it.elementType == CLASS_KEYWORD ||
                it.elementType == INTERFACE_KEYWORD ||
                it.elementType == OBJECT_KEYWORD
        } ?: return // can be null if this is ENUM_ENTRY

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
                emit(whitespace.endOffset, ERROR_MESSAGE.format("Unexpected"), false)
            }
            // multiple, report error if first line is not empty line
            else -> if ("\n\n" !in whitespace.text) {
                emit(whitespace.endOffset, ERROR_MESSAGE.format("Missing"), false)
            }
        }
    }
}
