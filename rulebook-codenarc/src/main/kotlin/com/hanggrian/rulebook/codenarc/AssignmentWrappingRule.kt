package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.syntax.Types.ASSIGN
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#assignment-wrapping) */
public class AssignmentWrappingRule : RulebookRule() {
    override fun getName(): String = "AssignmentWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "assignment.wrapping"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBinaryExpression(node: BinaryExpression) {
            super.visitBinaryExpression(node)

            // target multiline assignment
            val operation =
                node
                    .operation
                    .takeIf { it.type == ASSIGN && node.rightExpression.isMultiline() }
                    ?: return

            // checks for violation
            val expression =
                node
                    .rightExpression
                    .takeUnless { it.lineNumber == operation.startLine + 1 }
                    ?: return
            addViolation(expression, Messages[MSG])
        }
    }
}
