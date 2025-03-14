package com.hanggrian.rulebook.codenarc.internals

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codenarc.rule.AbstractAstVisitor

internal fun AnnotatedNode.hasAnnotation(name: String): Boolean =
    annotations.any { it.classNode.name == name }

internal fun Statement.hasReturnOrThrow(): Boolean {
    val statements =
        when (this) {
            is IfStatement -> ifBlock
            is CaseStatement -> code
            else -> return false
        }
    if (statements.isReturnOrThrow()) {
        return true
    }
    return (statements as? BlockStatement)
        ?.statements
        .orEmpty()
        .any { it.isReturnOrThrow() }
}

internal fun ASTNode.isMultiline(): Boolean = lastLineNumber > lineNumber

private fun Statement.isReturnOrThrow() = this is ReturnStatement || this is ThrowStatement

/**
 * @see org.codenarc.rule.convention.LongLiteralWithLowerCaseLAstVisitor
 */
internal fun AbstractAstVisitor.literalOf(expression: ConstantExpression): String? =
    sourceCode.lines[expression.lineNumber - 1]
        .takeUnless { it.length > expression.lastColumnNumber }
        ?.substring(expression.columnNumber - 1, expression.lastColumnNumber - 1)
