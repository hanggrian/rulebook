package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.syntax.Types.ASSIGN
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.Violation

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#operator-wrap) */
public class OperatorWrapRule : RulebookAstRule() {
    override fun getName(): String = "OperatorWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_MISSING = "operator.wrap.missing"
        const val MSG_UNEXPECTED = "operator.wrap.unexpected"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBinaryExpression(node: BinaryExpression) {
            super.visitBinaryExpression(node)

            // target multiline statement
            val operation =
                node
                    .takeIf { it.isMultiline() }
                    ?.operation
                    ?.takeUnless { it.type == ASSIGN }
                    ?: return

            // left expression may be a compounded binary expression
            val leftExpression =
                (node.leftExpression as? BinaryExpression)?.rightExpression
                    ?: node.leftExpression

            // checks for violation
            if (leftExpression.lastLineNumber < operation.startLine) {
                violations +=
                    Violation().apply {
                        rule = this@Visitor.rule
                        lineNumber = operation.startLine
                        sourceLine = sourceLine(node.rightExpression)
                        message = Messages.get(MSG_UNEXPECTED, operation.text)
                    }
                return
            }
            node
                .rightExpression
                .takeIf { it.lineNumber == operation.startLine }
                ?: return
            violations +=
                Violation().apply {
                    rule = this@Visitor.rule
                    lineNumber = operation.startLine
                    sourceLine = sourceLine(node.rightExpression)
                    message = Messages.get(MSG_MISSING, operation.text)
                }
        }
    }
}
