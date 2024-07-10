package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.syntax.Types.ASSIGN
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#assignment-wrapping)
 */
public class AssignmentWrappingRule : Rule() {
    override fun getName(): String = "AssignmentWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "assignment.wrapping"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBinaryExpression(expression: BinaryExpression) {
            // target multiline assignment
            val operation =
                expression.operation
                    .takeIf { it.type == ASSIGN }
                    ?.takeUnless { expression.lineNumber == expression.lastLineNumber }
                    ?: return

            // checks for violation
            val assignee = expression.rightExpression
            assignee.takeUnless { it.lineNumber == operation.startLine + 1 } ?: return
            addViolation(assignee, Messages[MSG])

            super.visitBinaryExpression(expression)
        }
    }
}
