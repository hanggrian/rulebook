@file:JvmName("NodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_BREAK
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_CONTINUE
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST
import com.puppycrawl.tools.checkstyle.utils.TokenUtil.isCommentType
import kotlin.math.max
import kotlin.math.min

/** Iterate to find the highest line number of this tree. */
internal val DetailAST.maxLineNo: Int
    get() =
        if (isLeaf()) {
            lineNo
        } else {
            max(lineNo, children().maxOf { it.maxLineNo })
        }

/** Iterate to find the lowest line number of this tree. */
internal val DetailAST.minLineNo: Int
    get() =
        if (isLeaf()) {
            lineNo
        } else {
            min(lineNo, children().minOf { it.minLineNo })
        }

/** Iterate to find the first leaf of the tree. */
internal fun DetailAST.firstLeaf(): DetailAST {
    var last = this
    while (last.firstChild != null) {
        last = last.firstChild
    }
    return last
}

/** Iterate to find the last leaf of the tree. */
internal fun DetailAST.lastLeaf(): DetailAST {
    var last = this
    while (last.lastChild != null) {
        last = last.lastChild
    }
    return last
}

/** Collect child nodes. */
internal fun DetailAST.children(): Sequence<DetailAST> =
    generateSequence(firstChild) { it.nextSibling }

/** Returns true if child node can be found in parent node. */
internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

/** Returns the parent node matching predicate, or null if not found. */
internal fun DetailAST.parent(predicate: (DetailAST) -> Boolean): DetailAST? {
    var node: DetailAST? = parent
    while (node != null) {
        if (predicate(node)) {
            return node
        }
        node = node.parent
    }
    return null
}

/** Returns the next sibling node matching predicate, or null if not found. */
internal fun DetailAST.nextSibling(predicate: (DetailAST) -> Boolean): DetailAST? {
    var node: DetailAST? = nextSibling
    while (node != null) {
        if (predicate(node)) {
            return node
        }
        node = node.nextSibling
    }
    return null
}

/** Returns true if modifier can be found in the node's modifier list. */
internal fun DetailAST.hasModifier(type: Int): Boolean =
    findFirstToken(MODIFIERS)
        ?.let { type in it }
        ?: false

/** Returns true if name can be found in the node's annotation list. */
internal fun DetailAST.hasAnnotation(name: String): Boolean =
    findFirstToken(MODIFIERS)
        ?.children()
        .orEmpty()
        .any { it.type == ANNOTATION && it.findFirstToken(IDENT)?.text.orEmpty() == name }

/**
 * Returns true if this node or code block of this node contains a jump statement.
 * In Java and Groovy, a switch-case branch may have a break statement without exiting current code
 * block.
 */
internal fun DetailAST.hasJumpStatement(includeBreakStatement: Boolean = true): Boolean =
    (findFirstToken(SLIST) ?: this).let {
        var predicate =
            LITERAL_RETURN in it ||
                LITERAL_THROW in it ||
                LITERAL_CONTINUE in it
        if (includeBreakStatement) {
            predicate = predicate || LITERAL_BREAK in it
        }
        predicate
    }

/** Returns true if this node has no children. */
internal fun DetailAST.isLeaf(): Boolean = childCount == 0

/** Determine whether this node spans multiple lines of code. */
internal fun DetailAST.isMultiline(): Boolean = maxLineNo > minLineNo

/** Returns true if this node is of any comment type. */
internal fun DetailAST.isComment(): Boolean = isCommentType(type)

/** Returns true if this node is an EOL comment without content. */
internal fun DetailAST.isEolCommentEmpty(): Boolean =
    type == SINGLE_LINE_COMMENT &&
        firstChild.let { n -> n.type == COMMENT_CONTENT && n.text == "\n" }
