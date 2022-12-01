// backport to main Internals.kt

package com.hendraanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode

internal val DetailNode.actualText: String
    get() = when {
        children.isEmpty() -> text
        else -> buildString { children.forEach { append(it.actualText) } }
    }

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

internal infix fun DetailAST.first(type: Int): DetailAST = findFirstToken(type)!!

internal infix fun DetailNode.first(type: Int): DetailNode = children.first { it.type == type }

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
