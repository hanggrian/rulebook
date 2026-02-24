package com.hanggrian.rulebook.codenarc

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.util.AstUtil.findFirstNonAnnotationLine

/**
 * This function replace `lineNumber` because it cannot accurately produce the first index
 * if component has a block comment.
 */
internal fun AbstractAstVisitor.getLineNumberBefore(current: ASTNode, previous: ASTNode): Int {
    var index = current.lineNumber - 1
    while (index > 0) {
        if (sourceCode.line(index).isBlank() || index == previous.lastLineNumber - 1) {
            return index + 1
        }
        index--
    }
    return current.lineNumber - 1
}

internal fun AbstractAstVisitor.hasCommentAbove(current: ASTNode): Boolean {
    var index = current.lineNumber - 1
    while (index > 0) {
        val line = sourceCode.line(index).trim()
        if (line.isBlank()) {
            return false
        }
        if (line.startsWith("//") ||
            line.startsWith("/*") ||
            line.startsWith("*")
        ) {
            return true
        }
        index--
    }
    return false
}

/**
 * @see org.codenarc.rule.convention.LongLiteralWithLowerCaseLAstVisitor
 */
internal fun AbstractAstVisitor.getLiteral(expression: ConstantExpression): String? =
    sourceCode.lines[expression.lineNumber - 1]
        .takeUnless { it.length > expression.lastColumnNumber }
        ?.substring(expression.lastColumnNumber - 2, expression.lastColumnNumber - 1)

/**
 * @see AbstractAstVisitor.sourceLine
 */
internal fun AbstractAstVisitor.sourceLineNullable(node: ASTNode): String? =
    when {
        node.lineNumber < 0 -> null
        else -> sourceCode.lines[findFirstNonAnnotationLine(node, sourceCode) - 1]
    }

/**
 * @see AbstractAstVisitor.lastSourceLine
 */
internal fun AbstractAstVisitor.lastSourceLineNullable(node: ASTNode): String? =
    when {
        node.lastLineNumber < 0 -> null
        else -> sourceCode.lines[node.lastLineNumber - 1]
    }
