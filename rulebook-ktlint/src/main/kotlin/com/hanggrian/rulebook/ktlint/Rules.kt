package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.psi.KtFile

/**
 * @see `com.pinterest.ktlint.ruleset.standard.FilenameRule`.
 */
internal fun Rule.getFileName(node: ASTNode): String? {
    node as? FileASTNode ?: error("node is not ${FileASTNode::class} but ${node::class}")
    val filePath = (node.psi as? KtFile)?.virtualFilePath
    if (filePath?.endsWith(".kt") != true || filePath.endsWith("package.kt")) {
        // ignore all non ".kt" files (including ".kts")
        stopTraversalOfAST()
        return null
    }
    return filePath
        .replace('\\', '/') // ensure compatibility with Windows OS
        .substringAfterLast("/")
        .substringBefore(".")
}
