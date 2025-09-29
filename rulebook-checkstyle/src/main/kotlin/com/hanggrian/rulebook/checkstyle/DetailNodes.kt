@file:JvmName("DetailNodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode

internal fun DetailNode.nextSibling(): DetailNode? =
    parent.children.let { it.getOrNull(it.indexOf(this) + 1) }
