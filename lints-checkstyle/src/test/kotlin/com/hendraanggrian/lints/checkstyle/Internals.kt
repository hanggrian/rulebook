package com.hendraanggrian.lints.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode

// backport to test
internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

// backport to test
internal val DetailNode.actualText: String
    get() = if (children.isEmpty()) {
        text
    } else {
        buildString { children.forEach { append(it.actualText) } }
    }
