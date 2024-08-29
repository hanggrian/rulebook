@file:JvmName("NodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION
import com.puppycrawl.tools.checkstyle.api.TokenTypes.BLOCK_COMMENT_BEGIN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.TYPE

internal val DetailAST.maxLineNo: Int
    get() {
        if (isLeaf()) {
            return lineNo
        }
        return children.maxOf { it.maxLineNo }
    }

internal val DetailAST.minLineNo: Int
    get() {
        if (isLeaf()) {
            return lineNo
        }
        return children.minOf { it.minLineNo }
    }

internal val DetailAST.children: Sequence<DetailAST>
    get() = generateSequence(firstChild) { node -> node.nextSibling }

internal val DetailAST.orComment: DetailAST
    get() {
        findFirstToken(SINGLE_LINE_COMMENT)?.let { return it }
        findFirstToken(BLOCK_COMMENT_BEGIN)?.let { return it }
        findFirstToken(TYPE)?.run {
            (findFirstToken(SINGLE_LINE_COMMENT) ?: findFirstToken(BLOCK_COMMENT_BEGIN))
                ?.let { return it }
        }

        var current = this
        while (!current.isLeaf()) {
            if (current.type == SINGLE_LINE_COMMENT ||
                current.type == BLOCK_COMMENT_BEGIN
            ) {
                return current
            }
            current = current.firstChild
        }
        return this
    }

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal fun DetailAST.hasModifier(type: Int): Boolean =
    findFirstToken(MODIFIERS)
        ?.let { type in it }
        ?: false

internal fun DetailAST.hasAnnotation(name: String): Boolean =
    findFirstToken(MODIFIERS)
        ?.children
        ?.any { it.type == ANNOTATION && it.findFirstToken(IDENT)?.text.orEmpty() == name }
        ?: false

internal fun DetailAST.hasReturnOrThrow(): Boolean {
    var statements = this
    statements
        .findFirstToken(SLIST)
        ?.let { statements = it }
    return LITERAL_RETURN in statements || LITERAL_THROW in statements
}

internal fun DetailAST.isLeaf(): Boolean = childCount == 0

internal fun DetailAST.isMultiline(): Boolean = maxLineNo > lineNo

internal fun DetailAST.isEolCommentEmpty(): Boolean =
    type == SINGLE_LINE_COMMENT &&
        firstChild.let { n -> n.type == COMMENT_CONTENT && n.text == "\n" }

/** In Checkstyle, nodes with children do not have text. */
internal fun DetailAST.joinText(separator: String = "", excludeType: Int = 0): String {
    val result =
        when {
            isLeaf() -> text
            else ->
                buildString {
                    for (child in children) {
                        if (child.type == excludeType) {
                            continue
                        }
                        append(child.joinText(separator, excludeType))
                        append(separator)
                    }
                }
        }
    if (result.endsWith(separator)) {
        return result.substring(0, result.length - separator.length)
    }
    return result
}
