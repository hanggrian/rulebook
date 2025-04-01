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

    internal companion object {
        const val MSG = "assignment.wrap"
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
                    .takeUnless {
                        it is ListExpression ||
                            it is MapExpression ||
                            it is ClosureExpression
                    }?.takeUnless { it.lineNumber == operation.startLine + 1 }
                    ?: return
            addViolation(expression, Messages[MSG])
        }
    }
}
