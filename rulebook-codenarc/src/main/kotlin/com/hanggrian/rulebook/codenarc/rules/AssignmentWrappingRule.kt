package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.AssignmentWrappingRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.codenarc.rules.AssignmentWrappingRule.Companion.MSG_UNEXPECTED
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.syntax.Types.ASSIGN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#assignment-wrapping) */
public class AssignmentWrappingRule : RulebookAstRule() {
    override fun getName(): String = "AssignmentWrapping"

    override fun getAstVisitorClass(): Class<*> = AssignmentWrappingVisitor::class.java

    internal companion object {
        const val MSG_MISSING = "assignment.wrapping.missing"
        const val MSG_UNEXPECTED = "assignment.wrapping.unexpected"
    }
}

public class AssignmentWrappingVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // find multiline assignee
        val expression = node.rightExpression ?: return super.visitBinaryExpression(node)
        val operation =
            node
                .operation
                .takeIf { it.type == ASSIGN && expression.isMultiline() }
                ?: return super.visitBinaryExpression(node)

        // checks for violation
        expression
            .takeIf {
                it is ListExpression ||
                    it is MapExpression ||
                    it is ClosureExpression
            }?.let {
                expression
                    .takeUnless { it.lineNumber == operation.startLine }
                    ?: return super.visitBinaryExpression(node)
                addViolation(expression, Messages[MSG_UNEXPECTED])
            }
        expression
            .takeUnless { it.lineNumber == operation.startLine + 1 }
            ?: return super.visitBinaryExpression(node)
        addViolation(expression, Messages[MSG_MISSING])

        super.visitBinaryExpression(node)
    }
}
