package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import com.hanggrian.rulebook.codenarc.internals.trimComment
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#trailing-comma-in-call) */
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

            // find parameters
            val expressions =
                (node.arguments as? ArgumentListExpression)
                    ?.expressions
                    ?: return

            // checks for violation
            if (expressions.isNotEmpty() && !node.isMultiline()) {
                val expression = expressions.last()
                sourceLine(expression)
                    .takeIf { it.trimComment().endsWith(",)") }
                    ?: return
                addViolation(expression, Messages[MSG_SINGLE])
                return
            }
            expressions
                .filterNot { lastSourceLine(it).trimComment().endsWith(',') }
                .forEach { addViolation(it, Messages[MSG_MULTI]) }
        }
    }
}
