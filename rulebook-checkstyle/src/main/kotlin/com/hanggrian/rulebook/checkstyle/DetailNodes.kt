@file:JvmName("DetailNodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle

import com.puppycrawl.tools.checkstyle.api.DetailNode

/** Collect child nodes. */
internal fun DetailNode.children(): Sequence<DetailNode> =
    generateSequence(firstChild) { it.nextSibling }
