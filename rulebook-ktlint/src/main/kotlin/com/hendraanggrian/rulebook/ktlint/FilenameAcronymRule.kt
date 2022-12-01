package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.core.KtLint
import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.FILE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import java.nio.file.Paths

/**
 * [See guide](https://github.com/hendraanggrian/rulebook/blob/main/rules.md#filename-acronym).
 */
class FilenameAcronymRule : Rule("filename-acronym") {
    internal companion object {
        const val ERROR_MESSAGE = "Acronym should be lowercase."
    }

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        // first line of filter
        if (node.elementType != FILE) {
            return
        }

        // get filename, obtained from `com.pinterest.ktlint.ruleset.standard.FilenameRule`
        node as FileASTNode? ?: error("node is not ${FileASTNode::class} but ${node::class}")
        val filePath = node.getUserData(KtLint.FILE_PATH_USER_DATA_KEY)
        if (filePath?.endsWith(".kt") != true) {
            stopTraversalOfAST() // ignore all non ".kt" files (including ".kts")
            return
        }
        val fileName = Paths.get(filePath).fileName.toString().substringBefore(".")
        if (fileName == "package") {
            stopTraversalOfAST() // ignore package.kt filename
            return
        }

        // find connecting uppercase letter
        for (i in 0 until fileName.length - 2) {
            if (fileName[i].isUpperCase() &&
                fileName[i + 1].isUpperCase() &&
                fileName[i + 2].isUpperCase()
            ) {
                emit(node.startOffset, ERROR_MESSAGE, false)
                return
            }
        }
    }
}
