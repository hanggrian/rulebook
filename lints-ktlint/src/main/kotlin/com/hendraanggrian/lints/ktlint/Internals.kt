package com.hendraanggrian.lints.ktlint

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null
