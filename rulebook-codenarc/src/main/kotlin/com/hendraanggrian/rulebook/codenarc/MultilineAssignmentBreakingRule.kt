package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.syntax.Types.ASSIGN
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#multiline-assignment-breaking)
 */
public class MultilineAssignmentBreakingRule : Rule() {
    override fun getName(): String = "MultilineAssignmentBreaking"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "multiline.assignment.breaking"
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
