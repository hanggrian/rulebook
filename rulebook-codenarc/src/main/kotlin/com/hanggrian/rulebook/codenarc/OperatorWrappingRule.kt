package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#operator-wrapping) */
public class OperatorWrappingRule : RulebookRule() {
    override fun getName(): String = "OperatorWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_MISSING = "operator.wrapping.missing"
        const val MSG_UNEXPECTED = "operator.wrapping.unexpected"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBinaryExpression(node: BinaryExpression) {
            super.visitBinaryExpression(node)

            // target multiline statement
            val operation =
                node
                    .takeIf { it.isMultiline() }
                    ?.operation
                    ?: return

            // left expression may be a compounded binary expression
            val leftExpression =
                (node.leftExpression as? BinaryExpression)?.rightExpression
                    ?: node.leftExpression

            // checks for violation
            if (leftExpression.lastLineNumber < operation.startLine) {
                addViolation(leftExpression, Messages.get(MSG_UNEXPECTED, operation.text))
                return
            }
            node
                .rightExpression
                .takeIf { it.lineNumber == operation.startLine }
                ?: return
            addViolation(node.rightExpression, Messages.get(MSG_MISSING, operation.text))
        }
    }
}
