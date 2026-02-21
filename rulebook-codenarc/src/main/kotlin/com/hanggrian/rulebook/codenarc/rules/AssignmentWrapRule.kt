package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.AssignmentWrapRule.Companion.MSG
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.syntax.Types.ASSIGN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrap) */
public class AssignmentWrapRule : RulebookAstRule() {
    override fun getName(): String = "AssignmentWrap"

    override fun getAstVisitorClass(): Class<*> = AssignmentWrapVisitor::class.java

    internal companion object {
        const val MSG = "assignment.wrap"
    }
}

public class AssignmentWrapVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip lambda and collection initializers
        val expression =
            node
                .rightExpression
                .takeUnless {
                    it is ListExpression ||
                        it is MapExpression ||
                        it is ClosureExpression
                } ?: return super.visitBinaryExpression(node)

        // find multiline assignee
        val operation =
            node
                .operation
                .takeIf { it.type == ASSIGN && expression.isMultiline() }
                ?: return super.visitBinaryExpression(node)

        // checks for violation
        expression
            .lineNumber
            .takeIf { it == operation.startLine }
            ?: return super.visitBinaryExpression(node)
        addViolation(expression, Messages[MSG])

        super.visitBinaryExpression(node)
    }
}
