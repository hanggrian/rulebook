package com.hendraanggrian.lints.ktlint

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal operator fun ASTNode.get(type: IElementType): ASTNode =
    checkNotNull(findChildByType(type)) {
        "No type ${type.javaClass.simpleName} found in this node."
    }

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength
