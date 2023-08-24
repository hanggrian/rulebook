package com.hendraanggrian.rulebook.ktlint

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.*

internal inline val ASTNode.endOffset: Int get() = startOffset + textLength

internal inline fun ASTNode.getOrNull(type: IElementType): ASTNode? = findChildByType(type)
internal inline operator fun ASTNode.contains(type: IElementType): Boolean =
    findChildByType(type) != null

internal fun ASTNode.siblingsUntil(type: IElementType): List<ASTNode> {
    val list = mutableListOf<ASTNode>()
    var next = treeNext
    while (next != null && next.elementType != type) {
        list += next
        next = next.treeNext
    }
    return list
}

internal object Messages {
    private const val FILENAME = "messages"
    private lateinit var bundleRef: WeakReference<ResourceBundle>

    operator fun get(key: String): String = when {
        ::bundleRef.isInitialized && bundleRef.get() != null -> bundleRef.get()!!
        else -> ResourceBundle.getBundle(FILENAME).also { bundleRef = WeakReference(it) }
    }.getString(key)

    fun get(key: String, vararg args: String): String = MessageFormat(get(key)).format(args)
}
