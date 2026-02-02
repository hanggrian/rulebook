package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BREAK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONTINUE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.children20
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline20
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/** Returns the node's position from the end. */
internal val ASTNode.endOffset: Int
    get() = startOffset + textLength

/** Iterate to find the last leaf of the tree. */
internal fun ASTNode.lastLeaf(): ASTNode {
    var last = this
    while (last.lastChildNode != null) {
        last = last.lastChildNode
    }
    return last
}

/** Collect sibling nodes until child node is found. */
internal fun ASTNode.siblingsUntil(type: IElementType): Sequence<ASTNode> =
    generateSequence(treeNext) { it.treeNext }
        .takeWhile { it.elementType !== type }

/** Returns true if child node can be found in parent node. */
internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

/** Returns true if this node or code block of this node contains a jump statement. */
internal fun ASTNode.hasJumpStatement(): Boolean =
    (findChildByType(BLOCK) ?: this)
        .let { RETURN in it || THROW in it || BREAK in it || CONTINUE in it }

/** Determine whether this node spans multiple lines of code. */
internal fun ASTNode.isMultiline(): Boolean =
    when {
        isWhiteSpaceWithNewline20 -> true
        else -> children20.any { it.isMultiline() }
    }

/** Returns true if this node is of any comment type. */
internal fun ASTNode.isComment(): Boolean =
    elementType === EOL_COMMENT || elementType === BLOCK_COMMENT || elementType === KDOC

/** Returns true if this node is whitespace with single blank line. */
internal fun ASTNode.isWhitespaceSingleLine(): Boolean =
    elementType === WHITE_SPACE && text.count { it == '\n' } == 1

/** Returns true if this node is whitespace with multiple blank lines. */
internal fun ASTNode.isWhitespaceMultiline(): Boolean =
    elementType === WHITE_SPACE && text.count { it == '\n' } > 1

/** Returns true if this node is an EOL comment without content. */
internal fun ASTNode.isEolCommentEmpty(): Boolean =
    elementType === EOL_COMMENT && text.substringAfter("//").isBlank()
