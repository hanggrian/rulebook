package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.lastSourceLineNullable
import com.hanggrian.rulebook.codenarc.sourceLineNullable
import com.hanggrian.rulebook.codenarc.trimComment
import com.hanggrian.rulebook.codenarc.visitors.RulebookAnyCallVisitor
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall

/** [See detail](https://hanggrian.github.io/rulebook/rules/#trailing-comma-in-call) */
public class TrailingCommaInCallRule : RulebookAstRule() {
    override fun getName(): String = "TrailingCommaInCall"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_SINGLE = "trailing.comma.in.call.single"
        const val MSG_MULTI = "trailing.comma.in.call.multi"
    }

    public class Visitor : RulebookAnyCallVisitor() {
        override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
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
