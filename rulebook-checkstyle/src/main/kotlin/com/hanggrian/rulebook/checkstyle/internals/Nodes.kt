@file:JvmName("NodesKt")
@file:JvmMultifileClass

package com.hanggrian.rulebook.checkstyle.internals

import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION
import com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN
import com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW
import com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SINGLE_LINE_COMMENT
import com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST

internal val DetailAST.firstMostChild: DetailAST
    get() {
        var last = this
        while (last.firstChild != null) {
            last = last.firstChild
        }
        return last
    }

internal val DetailAST.lastMostChild: DetailAST
    get() {
        var last = this
        while (last.lastChild != null) {
            last = last.lastChild
        }
        return last
    }

internal val DetailAST.children: Sequence<DetailAST>
    get() = generateSequence(firstChild) { node -> node.nextSibling }

internal val DetailAST.nextSiblings: Sequence<DetailAST>
    get() = generateSequence(nextSibling) { it.nextSibling }

internal operator fun DetailAST.contains(type: Int): Boolean = findFirstToken(type) != null

internal fun DetailAST.hasModifier(type: Int): Boolean =
    MODIFIERS in this &&
        type in findFirstToken(MODIFIERS)!!

internal fun DetailAST.hasAnnotation(name: String): Boolean =
    MODIFIERS in this &&
        findFirstToken(MODIFIERS)
            .children
            .any { it.type == ANNOTATION && it.findFirstToken(IDENT)?.text.orEmpty() == name }

internal fun DetailAST.hasReturnOrThrow(): Boolean {
    var statements = this
    if (SLIST in statements) {
        statements = statements.findFirstToken(SLIST)!!
    }
    return LITERAL_RETURN in statements || LITERAL_THROW in statements
}

internal fun DetailAST.isMultiline(): Boolean = lastMostChild.lineNo > lineNo

internal fun DetailAST.isEolCommentEmpty(): Boolean =
    type == SINGLE_LINE_COMMENT &&
        firstChild.let { n -> n.type == COMMENT_CONTENT && n.text == "\n" }

/** In Checkstyle, nodes with children do not have text. */
internal fun DetailAST.joinText(separator: String = "", excludeType: Int = 0): String {
    val children = children
    val result =
        when {
            children.count() == 0 -> text
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
