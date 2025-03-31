package com.hanggrian.rulebook.codenarc.internals

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/**
 * This function replace `lineNumber` because it cannot accurately produce the first index
 * if component has a block comment.
 */
internal fun AbstractAstVisitor.getLineNumberAfter(current: ASTNode, previous: ASTNode): Int {
    var index = current.lineNumber - 1
    while (index > 0) {
        if (sourceCode.line(index).isBlank() || index == previous.lastLineNumber - 1) {
            return index + 1
        }
        index--
    }
    return current.lineNumber - 1
}

/**
 * @see org.codenarc.rule.convention.LongLiteralWithLowerCaseLAstVisitor
 */
internal fun AbstractAstVisitor.getLiteral(expression: ConstantExpression): String? =
    sourceCode.lines[expression.lineNumber - 1]
        .takeUnless { it.length > expression.lastColumnNumber }
        ?.substring(expression.columnNumber - 1, expression.lastColumnNumber - 1)
