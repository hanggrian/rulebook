package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_BODY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERFACE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.OBJECT_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.psiUtil.children

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/AddBlankLineInClass).
 */
class AddBlankLineInClassRule : RulebookRule("add-blank-lines-in-class") {
    internal companion object {
        const val MSG = "add.blank.line.in.class"
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

        // get sibling whitespace
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
        if ("\n\n" !in whitespace.text) {
            emit(whitespace.endOffset, Messages[MSG], false)
        }
    }
}
