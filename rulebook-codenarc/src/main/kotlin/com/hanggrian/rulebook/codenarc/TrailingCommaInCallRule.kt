package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import com.hanggrian.rulebook.codenarc.internals.trimComment
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.Violation

/** [See detail](https://hanggrian.github.io/rulebook/rules/#trailing-comma-in-call) */
public class TrailingCommaInCallRule : RulebookAstRule() {
    override fun getName(): String = "TrailingCommaInCall"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_SINGLE = "trailing.comma.in.call.single"
        const val MSG_MULTI = "trailing.comma.in.call.multi"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)

            // find last parameter
            val arguments = node.arguments as? ArgumentListExpression ?: return
            val expression = arguments.expressions?.lastOrNull() ?: return

            // checks for violation
            if (!arguments.isMultiline()) {
                sourceLine(expression)
                    .takeIf { it.trimComment().endsWith(",)") }
                    ?: return
                addViolation(expression, Messages[MSG_SINGLE])
                return
            }
            lastSourceLine(expression)
                .takeUnless { it.trimComment().endsWith(',') }
                ?: return
            violations +=
                Violation().apply {
                    rule = this@Visitor.rule
                    lineNumber = expression.lastLineNumber
                    sourceLine = lastSourceLine(expression)
                    message = Messages[MSG_MULTI]
                }
        }
    }
}
