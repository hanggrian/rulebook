package com.hendraanggrian.lints.ktlint

import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal operator fun ASTNode.get(type: IElementType): ASTNode = findChildByType(type)!!

internal infix fun ASTNode.firstOrNull(type: IElementType): ASTNode? =
    children().firstOrNull { it.elementType == type }

internal fun ASTNode.siblingsUntil(type: IElementType): List<ASTNode> {
    val list = mutableListOf<ASTNode>()
    var next = treeNext
    while (next != null && next.elementType != type) {
        list += next
        next = next.treeNext
    }
    return list
}
