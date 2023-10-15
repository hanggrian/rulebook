package com.hendraanggrian.rulebook.ktlint

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.*

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

internal fun String.isStaticPropertyName(): Boolean =
    all { it.isUpperCase() || it.isDigit() || it == '_' }

internal object Messages {
    private const val FILENAME = "messages"
    private lateinit var bundleRef: WeakReference<ResourceBundle>

    operator fun get(key: String): String = bundle.getString(key)

    fun get(key: String, vararg args: String): String =
        MessageFormat(bundle.getString(key)).format(args)

    private val bundle: ResourceBundle
        get() {
            var bundle: ResourceBundle?
            if (::bundleRef.isInitialized) {
                bundle = bundleRef.get()
                if (bundle != null) {
                    return bundle
                }
            }
            bundle = ResourceBundle.getBundle(FILENAME)
            bundleRef = WeakReference(bundle)
            return bundle
        }
}
