package com.hendraanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.DetailNode
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes.EOF

internal fun DetailNode.find(type: Int): DetailNode? = children.firstOrNull { it.type == type }

internal operator fun DetailNode.contains(type: Int): Boolean = children.any { it.type == type }

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

/** In Checkstyle Javadoc, nodes with children do not have text any may contain '<EOF>'. */
internal val DetailNode.actualText: String
    get() =
        when {
            children.isEmpty() -> text
            else ->
                buildString {
                    children.forEach {
                        if (it.type != EOF) {
                            append(it.actualText)
                        }
                    }
                }
        }

/** In Checkstyle, nodes with children do not have text. */
internal val DetailAST.actualText: String
    get() {
        val children = children().toList()
        return when {
            children.isEmpty() -> text
            else -> buildString { children.forEach { append(it.actualText) } }
        }
    }

internal fun DetailAST.children(): Sequence<DetailAST> =
    generateSequence(firstChild) { node -> node.nextSibling }

internal fun DetailAST.siblings(forward: Boolean = true): Sequence<DetailAST> =
    when {
        forward -> generateSequence(nextSibling) { it.nextSibling }
        else -> generateSequence(previousSibling) { it.previousSibling }
    }
