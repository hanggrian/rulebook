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

internal fun DetailAST.hasJumpStatement(): Boolean =
    (findFirstToken(SLIST) ?: this)
        .let {
            LITERAL_RETURN in it ||
                LITERAL_THROW in it ||
                LITERAL_BREAK in it ||
                LITERAL_CONTINUE in it
        }

internal fun DetailAST.isLeaf(): Boolean = childCount == 0

internal fun DetailAST.isMultiline(): Boolean = maxLineNo > minLineNo

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
