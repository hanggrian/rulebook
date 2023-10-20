package com.hendraanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailNode

/**
 * Returns the whole text of this node. Calling `getText()` on Java node with children returns its
 * type, e.g.: (JAVADOC_TAG). This function search node's children recursively for a valid text.
 */
internal val DetailNode.actualText: String
    get() =
        when {
            children.isEmpty() -> text
            else -> buildString { children.forEach { append(it.actualText) } }
        }

internal fun DetailNode.find(type: Int): DetailNode? = children.firstOrNull { it.type == type }

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }
