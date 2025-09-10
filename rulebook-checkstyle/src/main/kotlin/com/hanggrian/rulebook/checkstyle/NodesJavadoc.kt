@file:JvmName("NodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode

internal val DetailNode.nextSibling: DetailNode?
    get() = parent.children.let { it.getOrNull(it.indexOf(this) + 1) }
