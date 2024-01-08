package com.hendraanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.substringBefore
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.psi.KtFile

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal fun ASTNode.siblingsUntil(type: IElementType): List<ASTNode> {
    val list = mutableListOf<ASTNode>()
    var next = treeNext
    while (next != null && next.elementType != type) {
        list += next
        next = next.treeNext
    }
    return list
}

// get text without argument and nullability
internal val ASTNode.qualifierName: String get() = text.substringBefore('<').substringBefore('?')

// get filename, obtained from `com.pinterest.ktlint.ruleset.standard.FilenameRule`
internal fun Rule.getFileName(node: ASTNode): String? {
    node as FileASTNode?
        ?: error("node is not ${FileASTNode::class} but ${node::class}")
    val filePath = (node.psi as? KtFile)?.virtualFilePath
    if (filePath?.endsWith(".kt") != true || filePath.endsWith("package.kt")) {
        // ignore all non ".kt" files (including ".kts")
        stopTraversalOfAST()
        return null
    }
    return filePath.replace('\\', '/') // ensure compatibility with Windows OS
        .substringAfterLast("/")
        .substringBefore(".")
}
