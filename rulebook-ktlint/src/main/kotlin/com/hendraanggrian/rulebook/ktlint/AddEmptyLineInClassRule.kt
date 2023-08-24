package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERFACE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/AddEmptyLineInClass).
 */
class AddEmptyLineInClassRule : RulebookRule("add-empty-line-in-class") {
    internal companion object {
        const val MSG = "add.empty.line.in.class"
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

        // skip single-line class declaration
        val classDeclaration = classKeyword.siblingsUntil(CLASS_BODY).joinToString("") { it.text }
        if ("\n" !in classDeclaration) {
            return
        }

        // report error if first line is not empty line
        val classType = `class`.getOrNull(IDENTIFIER)?.text ?: "Companion object"
        if ("\n\n" !in whitespace.text) {
            emit(whitespace.endOffset, Messages.get(MSG, classType), false)
        }
    }
}
