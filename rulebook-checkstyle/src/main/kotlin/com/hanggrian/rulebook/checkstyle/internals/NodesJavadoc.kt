@file:JvmName("NodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailNode

internal val DetailNode.next: DetailNode?
    get() = parent.children.let { it.getOrNull(it.indexOf(this) + 1) }
