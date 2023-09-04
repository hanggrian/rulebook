// backport to main Internals.kt

package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode
import java.lang.ref.WeakReference
import java.text.MessageFormat
import java.util.*

/**
 * Returns the whole text of this node. Calling `getText()` on Java node with children returns its
 * type, e.g.: (JAVADOC_TAG). This function search node's children recursively for a valid text.
 */
internal val DetailNode.actualText: String
    get() = when {
        children.isEmpty() -> text
        else -> buildString { children.forEach { append(it.actualText) } }
    }

internal inline fun DetailAST.getOrNull(type: Int): DetailAST? = findFirstToken(type)
internal inline operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal inline fun DetailNode.getOrNull(type: Int): DetailNode? =
    children.first { it.type == type }

internal inline operator fun DetailNode.contains(type: Int): Boolean =
    children.any { it.type == type }

internal fun DetailNode.siblingsUntil(type: Int): List<DetailNode> {
    val siblings = parent.children
    val index = siblings.indexOf(this)
    val list = mutableListOf<DetailNode>()
    for (i in index + 1 until siblings.size) {
        val next = siblings[i]
        if (next.type == type) {
            return list
        }
        list += next
    }
    return list
}

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
            return bundle
        }
}
