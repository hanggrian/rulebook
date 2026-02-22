package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.OperatorWrapRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.codenarc.rules.OperatorWrapRule.Companion.MSG_UNEXPECTED
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.syntax.Types.ASSIGN

/** [See detail](https://hanggrian.github.io/rulebook/rules/#operator-wrap) */
public class OperatorWrapRule : RulebookAstRule() {
    override fun getName(): String = "OperatorWrap"

    override fun getAstVisitorClass(): Class<*> = OperatorWrapVisitor::class.java

    internal companion object {
        const val MSG_MISSING = "operator.wrap.missing"
        const val MSG_UNEXPECTED = "operator.wrap.unexpected"
    }
}

public class OperatorWrapVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // target multiline statement
        val operation =
            node
                .takeIf { it.isMultiline() }
                ?.operation
                ?.takeUnless { it.type == ASSIGN }
                ?: return super.visitBinaryExpression(node)

        // left expression may be a compounded binary expression
        val leftExpression =
            (node.leftExpression as? BinaryExpression)?.rightExpression
                ?: node.leftExpression

        // checks for violation
        operation
            .takeIf { leftExpression.lastLineNumber < it.startLine }
            ?.let {
                violations +=
                    rule.createViolation(
                        it.startLine,
                        sourceLine(node.rightExpression),
                        Messages[MSG_UNEXPECTED, it.text],
                    )
                return super.visitBinaryExpression(node)
            }
        node
            .rightExpression
            .takeUnless {
                it is ListExpression ||
                    it is MapExpression ||
                    it is ClosureExpression
            }?.lineNumber
            ?.takeIf { it == operation.startLine }
            ?: return super.visitBinaryExpression(node)
        violations +=
            rule.createViolation(
                operation.startLine,
                sourceLine(node.rightExpression),
                Messages[MSG_MISSING, operation.text],
            )

        super.visitBinaryExpression(node)
    }
}
