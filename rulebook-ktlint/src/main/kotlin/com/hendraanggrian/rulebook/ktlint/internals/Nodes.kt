package com.hendraanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.ElementType.CLASS
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.INTERNAL_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PRIVATE_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.ElementType.PROTECTED_KEYWORD
import com.pinterest.ktlint.rule.engine.core.api.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode
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

internal fun ASTNode.hasNonPublicOrTestParent(): Boolean {
    var next: ASTNode? = this
    while (next != null) {
        if (next.isNonPublic()) {
            return true
        }
        if (next.elementType == CLASS) {
            val identifier = next.findChildByType(IDENTIFIER) ?: continue
            if (identifier.text.endsWith("Test")) {
                return true
            }
        }
        next = next.treeParent
    }
    return false
}

private fun ASTNode.isNonPublic(): Boolean {
    val modifiers = findChildByType(MODIFIER_LIST) ?: return false
    return PRIVATE_KEYWORD in modifiers ||
        INTERNAL_KEYWORD in modifiers ||
        PROTECTED_KEYWORD in modifiers
}

// Get filename, obtained from `com.pinterest.ktlint.ruleset.standard.FilenameRule`.
internal fun Rule.getFileName(node: ASTNode): String? {
    node as FileASTNode?
        ?: error("node is not ${FileASTNode::class} but ${node::class}")
    val filePath = (node.psi as? KtFile)?.virtualFilePath
    if (filePath?.endsWith(".kt") != true || filePath.endsWith("package.kt")) {
        // Ignore all non ".kt" files (including ".kts").
        stopTraversalOfAST()
        return null
    }
    return filePath.replace('\\', '/') // Ensure compatibility with Windows OS.
        .substringAfterLast("/")
        .substringBefore(".")
}
