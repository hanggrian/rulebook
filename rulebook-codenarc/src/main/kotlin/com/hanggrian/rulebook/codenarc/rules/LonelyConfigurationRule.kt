package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.LonelyConfigurationRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.LonelyConfigurationRule.Companion.NAMED_DOMAIN_FUNCTIONS
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-configuration) */
public class LonelyConfigurationRule : RulebookAstRule() {
    override fun getName(): String = "LonelyConfiguration"

    override fun getAstVisitorClass(): Class<*> = LonelyConfigurationVisitor::class.java

    internal companion object {
        const val MSG = "lonely.configuration"

        val NAMED_DOMAIN_FUNCTIONS =
            setOf("repositories", "dependencies", "configurations", "tasks", "sourceSets")
    }
}

public class LonelyConfigurationVisitor : RulebookVisitor() {
    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (!isFirstVisit(node) || !isScript()) {
            return
        }

        // target named domain or handler with scope
        (node.method as? ConstantExpression)
            ?.takeIf { it.value in NAMED_DOMAIN_FUNCTIONS }
            ?: return super.visitMethodCallExpression(node)

        // checks for violation
        val expression =
            (node.arguments as? ArgumentListExpression)
                ?.expressions
                ?.singleOrNull()
                ?: return super.visitMethodCallExpression(node)
        val statement =
            ((expression as? ClosureExpression)?.code as? BlockStatement)
                ?.statements
                ?.singleOrNull()
                ?: return super.visitMethodCallExpression(node)
        val expression2 =
            (statement as? ExpressionStatement)
                ?.expression
                ?.takeIf { it is MethodCallExpression }
                ?: return super.visitMethodCallExpression(node)
        addViolation(expression2, Messages[MSG])

        super.visitMethodCallExpression(node)
    }
}
