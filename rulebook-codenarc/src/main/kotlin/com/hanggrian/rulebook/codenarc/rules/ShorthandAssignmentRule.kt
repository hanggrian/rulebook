package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ShorthandAssignmentRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.ShorthandAssignmentRule.Companion.SHORTHAND_OPERATIONS
import org.codehaus.groovy.ast.expr.BinaryExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#shorthand-assignment) */
public class ShorthandAssignmentRule : RulebookAstRule() {
    override fun getName(): String = "ShorthandAssignment"

    override fun getAstVisitorClass(): Class<*> = ShorthandAssignmentVisitor::class.java

    internal companion object {
        const val MSG = "shorthand.assignment"

        val SHORTHAND_OPERATIONS = setOf("+", "-", "*", "/", "%")
    }
}

public class ShorthandAssignmentVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip shorthand operator
        node
            .operation
            .takeIf { it.text == "=" }
            ?: return

        // checks for violation
        val identifier = node.leftExpression
        val binaryExpression =
            (node.rightExpression as? BinaryExpression)
                ?.deepestBinaryExpression
                ?: return
        val operation =
            binaryExpression
                .operation
                ?.takeIf { it.text in SHORTHAND_OPERATIONS }
                ?: return
        binaryExpression
            .leftExpression
            .takeIf { it.text == identifier.text }
            ?: return
        addViolation(node, Messages[MSG, operation.text + "="])

        super.visitBinaryExpression(node)
    }

    private companion object {
        val BinaryExpression.deepestBinaryExpression: BinaryExpression
            get() {
                var current = this
                while (current.leftExpression is BinaryExpression) {
                    current = current.leftExpression as BinaryExpression
                }
                return current
            }
    }
}
