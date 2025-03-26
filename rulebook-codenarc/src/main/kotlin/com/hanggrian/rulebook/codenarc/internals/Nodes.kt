package com.hanggrian.rulebook.codenarc.internals

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codenarc.rule.AbstractAstVisitor

internal fun AnnotatedNode.hasAnnotation(name: String): Boolean =
    annotations.any { it.classNode.name == name }

internal fun Statement.hasJumpStatement(): Boolean {
    val statements =
        when (this) {
            is IfStatement -> ifBlock
            is CaseStatement -> code
            else -> return false
        }
    return if (statements.isJumpStatement()) {
        true
    } else {
        (statements as? BlockStatement)
            ?.statements
            .orEmpty()
            .any { it.isJumpStatement() }
    }
}

internal fun ASTNode.isMultiline(): Boolean = lastLineNumber > lineNumber

private fun Statement.isJumpStatement() =
    this is ReturnStatement ||
        this is ThrowStatement ||
        this is BreakStatement ||
        this is ContinueStatement

/**
 * @see org.codenarc.rule.convention.LongLiteralWithLowerCaseLAstVisitor
 */
internal fun AbstractAstVisitor.literalOf(expression: ConstantExpression): String? =
    sourceCode.lines[expression.lineNumber - 1]
        .takeUnless { it.length > expression.lastColumnNumber }
        ?.substring(expression.columnNumber - 1, expression.lastColumnNumber - 1)
