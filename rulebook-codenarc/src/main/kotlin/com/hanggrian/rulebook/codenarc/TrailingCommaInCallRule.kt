package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import com.hanggrian.rulebook.codenarc.internals.lastSourceLineNullable
import com.hanggrian.rulebook.codenarc.internals.sourceLineNullable
import com.hanggrian.rulebook.codenarc.internals.trimComment
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#trailing-comma-in-call) */
public class TrailingCommaInCallRule : RulebookAstRule() {
    override fun getName(): String = "TrailingCommaInCall"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_SINGLE = "trailing.comma.in.call.single"
        const val MSG_MULTI = "trailing.comma.in.call.multi"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitConstructorCallExpression(node: ConstructorCallExpression) {
            super.visitConstructorCallExpression(node)
            visitAnyCallExpression(node)
        }

        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)
            visitAnyCallExpression(node)
        }

        private fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
            // find last parameter
            val arguments = node.arguments as? ArgumentListExpression ?: return
            val expression =
                arguments
                    .expressions
                    ?.lastOrNull { it !is ClosureExpression }
                    ?: return

            // checks for violation
            if (!arguments.isMultiline()) {
                sourceLineNullable(expression)
                    ?.trimComment()
                    ?.takeIf { it.endsWith(",)") }
                    ?: return
                addViolation(expression, Messages[MSG_SINGLE])
                return
            }
            lastSourceLineNullable(expression)
                ?.trimComment()
                ?.takeUnless { it.endsWith(',') }
                ?: return
            violations +=
                rule.createViolation(
                    expression.lastLineNumber,
                    lastSourceLine(expression),
                    Messages[MSG_MULTI],
                )
        }
    }
}
