package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ComplicatedSizeComparisonRule.Companion.MSG
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#complicated-size-comparison) */
public class ComplicatedSizeComparisonRule : RulebookAstRule() {
    override fun getName(): String = "ComplicatedSizeComparison"

    override fun getAstVisitorClass(): Class<*> = ComplicatedSizeComparisonVisitor::class.java

    internal companion object {
        const val MSG = "complicated.size.comparison"
    }
}

public class ComplicatedSizeComparisonVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        node
            .operation
            .text
            .takeIf { it == ">" || it == "<" || it == "==" }
            ?: return
        node
            .takeIf {
                it.leftExpression.isLiteralZero() ||
                    it.rightExpression.isLiteralZero()
            } ?: return
        val call =
            node.leftExpression as? MethodCallExpression
                ?: node.rightExpression as? MethodCallExpression
                ?: return
        addViolation(call, Messages[MSG])

        super.visitBinaryExpression(node)
    }

    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        val call =
            node
                .takeIf {
                    (it.arguments as? ArgumentListExpression)?.expressions?.isEmpty() == true
                }?.method
                ?.takeIf { it.text == "isEmpty" }
                ?: return
        addViolation(call, Messages[MSG])

        super.visitMethodCallExpression(node)
    }

    private fun Expression.isLiteralZero(): Boolean = (this as? ConstantExpression)?.value == 0
}
