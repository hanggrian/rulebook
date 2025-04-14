package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.syntax.Types.ASSIGN
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrap) */
public class AssignmentWrapRule : RulebookAstRule() {
    override fun getName(): String = "AssignmentWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "assignment.wrap"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBinaryExpression(node: BinaryExpression) {
            super.visitBinaryExpression(node)

            // skip lambda and collection initializers
            val expression =
                node
                    .rightExpression
                    .takeUnless {
                        it is ListExpression ||
                            it is MapExpression ||
                            it is ClosureExpression
                    } ?: return

            // find multiline assignee
            val operation =
                node
                    .operation
                    .takeIf { it.type == ASSIGN && expression.isMultiline() }
                    ?: return

            // checks for violation
            expression
                .lineNumber
                .takeIf { it == operation.startLine }
                ?: return
            addViolation(expression, Messages[MSG])
        }
    }
}
