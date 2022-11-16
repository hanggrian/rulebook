// backport to main Internals.kt

package com.hendraanggrian.lints.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

internal operator fun DetailAST.get(type: Int): DetailAST =
    checkNotNull(findFirstToken(type)) { "No type $type found in this node." }

internal operator fun DetailNode.get(type: Int): DetailNode =
    checkNotNull(children.firstOrNull { it.type == type }) { "No type $type found in this node." }

internal val DetailNode.actualText: String
    get() = if (children.isEmpty()) {
        text
    } else {
        buildString { children.forEach { append(it.actualText) } }
    }
