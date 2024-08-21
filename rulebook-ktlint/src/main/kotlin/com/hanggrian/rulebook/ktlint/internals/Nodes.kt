package com.hanggrian.rulebook.ktlint.internals

import com.pinterest.ktlint.rule.engine.core.api.ElementType.BLOCK
import com.pinterest.ktlint.rule.engine.core.api.ElementType.EOL_COMMENT
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IF
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
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
    val list = mutableListOf<ASTNode>()
    var next = treeNext
    while (next != null && next.elementType != type) {
        list += next
        next = next.treeNext
    }
    return list
}

internal fun ASTNode.hasModifier(type: IElementType): Boolean =
    findChildByType(MODIFIER_LIST)
        ?.let { type in it }
        ?: false

internal fun ASTNode.hasReturnOrThrow(): Boolean {
    var statements =
        when (elementType) {
            IF -> findChildByType(THEN)!!
            WHEN_ENTRY -> this
            else -> return false
        }
    statements
        .findChildByType(BLOCK)
        ?.let { statements = it }
    return RETURN in statements || THROW in statements
}

internal fun ASTNode.isMultiline(): Boolean {
    if (isWhiteSpaceWithNewline()) {
        return true
    }
    return children().any { it.isMultiline() }
}

internal fun ASTNode.isWhitespaceWithSingleNewline(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } == 1

internal fun ASTNode.isWhitespaceWithMultipleNewlines(): Boolean =
    elementType == WHITE_SPACE && text.count { it == '\n' } > 1

internal fun ASTNode.isEolCommentEmpty(): Boolean =
    elementType == EOL_COMMENT && text.substringAfter("//").isBlank()
