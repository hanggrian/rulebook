package com.hanggrian.rulebook.codenarc.internals

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.TupleExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.ThrowStatement

/** Returns true if name can be found in the node's annotation list. */
internal fun AnnotatedNode.hasAnnotation(name: String): Boolean =
    annotations.any { it.classNode.name == name }

/** Returns this node or first statement of this code block. */
internal fun Statement.firstStatement(): Statement =
    (this as? BlockStatement)
        ?.statements
        ?.firstOrNull()
        ?: this

/** Returns this node or last expression of this code block. */
internal fun Expression.lastExpression(): Expression =
    (this as? TupleExpression)
        ?.expressions
        ?.lastOrNull()
        ?: this

/**
 * Returns true if this node or code block of this node contains a jump statement.
 * In Java and Groovy, a switch-case branch may have a break statement without exiting current code
 * block.
 */
internal fun Statement.hasJumpStatement(includeBreakStatement: Boolean = true): Boolean =
    (this as? BlockStatement)
        ?.statements
        ?.any { it.isJumpStatement(includeBreakStatement) }
        ?: isJumpStatement(includeBreakStatement)

private fun Statement.isJumpStatement(includeBreakStatement: Boolean): Boolean {
    var predicate =
        this is ReturnStatement ||
            this is ThrowStatement ||
            this is ContinueStatement
    if (includeBreakStatement) {
        predicate = predicate || this is BreakStatement
    }
    return predicate
}

/** Determine whether this node spans multiple lines of code. */
internal fun ASTNode.isMultiline(): Boolean =
    when (this) {
        is IfStatement ->
            (elseBlock as? BlockStatement)?.isMultiline()
                ?: (ifBlock as? BlockStatement)?.isMultiline()
                ?: booleanExpression.isMultiline()

        is CaseStatement ->
            (code as? BlockStatement)?.isMultiline()
                ?: expression.isMultiline()

        is BlockStatement ->
            statements.isNotEmpty() &&
                statements.last().lastLineNumber > statements.first().lineNumber

        else -> lastLineNumber > lineNumber
    }
