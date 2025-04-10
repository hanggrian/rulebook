package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import com.hanggrian.rulebook.codenarc.internals.lastSourceLineNullable
import com.hanggrian.rulebook.codenarc.internals.sourceLineNullable
import com.hanggrian.rulebook.codenarc.internals.trimComment
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
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
        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)

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
                    ?.takeIf { it.trimComment().endsWith(",)") }
                    ?: return
                addViolation(expression, Messages[MSG_SINGLE])
                return
            }
            lastSourceLineNullable(expression)
                ?.takeUnless { it.trimComment().endsWith(',') }
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
