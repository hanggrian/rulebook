package com.hanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.BREAK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONTINUE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.KDOC
import com.pinterest.ktlint.rule.engine.core.api.ElementType.RETURN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THEN
import com.pinterest.ktlint.rule.engine.core.api.ElementType.THROW
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHEN_ENTRY
import com.pinterest.ktlint.rule.engine.core.api.ElementType.WHITE_SPACE
import com.pinterest.ktlint.rule.engine.core.api.children
import com.pinterest.ktlint.rule.engine.core.api.isWhiteSpaceWithNewline
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

internal val ASTNode.endOffset: Int
    get() = startOffset + textLength

internal val ASTNode.qualifierName: String
    get() = text.substringBefore('<').substringBefore('?')

internal val ASTNode.lastMostChild: ASTNode
    get() {
        var last = this
        while (last.lastChildNode != null) {
            last = last.lastChildNode
        }
        return last
    }

internal operator fun ASTNode.contains(type: IElementType): Boolean = findChildByType(type) != null

internal fun ASTNode.siblingsUntil(type: IElementType): List<ASTNode> {
    var next = treeNext
    return buildList {
        while (next != null && next.elementType != type) {
            add(next)
            next = next.treeNext
        }
    }
}

internal fun ASTNode.hasJumpStatement(): Boolean {
    val statements =
        when (elementType) {
            IF -> findChildByType(THEN)!!
            WHEN_ENTRY -> this
            else -> return false
        }
    return (statements.findChildByType(BLOCK) ?: statements)
        .let { RETURN in it || THROW in it || BREAK in it || CONTINUE in it }
}

internal fun ASTNode.isMultiline(): Boolean =
    if (isWhiteSpaceWithNewline()) {
        true
    } else {
        children().any { it.isMultiline() }
    }

internal fun ASTNode.isComment(): Boolean =
    elementType == EOL_COMMENT || elementType == BLOCK_COMMENT || elementType == KDOC

internal fun ASTNode.isWhitespaceSingleLine(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } == 1

internal fun ASTNode.isWhitespaceMultiline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } > 1

internal fun ASTNode.isEolCommentEmpty(): Boolean =
    elementType == EOL_COMMENT && text.substringAfter("//").isBlank()
