package com.hendraanggrian.lints.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

internal operator fun DetailAST.get(type: Int): DetailAST =
    checkNotNull(findFirstToken(type)) { "No type $type found in this node." }

internal operator fun DetailNode.get(type: Int): DetailNode =
    checkNotNull(children.firstOrNull { it.type == type }) { "No type $type found in this node." }

/**
 * Returns the whole text of this node. Calling `getText()` on Java node with children returns its
 * type, e.g.: (JAVADOC_TAG). This function search node's children recursively for a valid text.
 */
internal val DetailNode.actualText: String
    get() = when {
        children.isEmpty() -> text
        else -> buildString { children.forEach { append(it.actualText) } }
    }
