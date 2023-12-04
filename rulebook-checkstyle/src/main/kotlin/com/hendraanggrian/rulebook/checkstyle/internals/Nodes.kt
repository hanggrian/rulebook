package com.hendraanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode

internal fun DetailNode.find(type: Int): DetailNode? = children.firstOrNull { it.type == type }

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal fun DetailAST.children(): Sequence<DetailAST> =
    generateSequence(firstChild) { node -> node.nextSibling }

internal fun DetailAST.siblings(forward: Boolean = true): Sequence<DetailAST> =
    when {
        forward -> generateSequence(nextSibling) { it.nextSibling }
        else -> generateSequence(previousSibling) { it.previousSibling }
    }
