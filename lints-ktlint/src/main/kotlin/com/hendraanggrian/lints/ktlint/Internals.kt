package com.hendraanggrian.lints.ktlint

import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal operator fun ASTNode.get(type: IElementType): ASTNode = findChildByType(type)!!

internal infix fun ASTNode.firstOrNull(type: IElementType): ASTNode? =
    children().firstOrNull { it.elementType == type }

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength
