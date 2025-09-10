package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.visitors.RulebookAnyCallVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall

/** [See detail](https://hanggrian.github.io/rulebook/rules/#parentheses-trim) */
public class ParenthesesTrimRule : RulebookAstRule() {
    override fun getName(): String = "ParenthesesTrim"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_FIRST = "parentheses.trim.first"
        const val MSG_LAST = "parentheses.trim.last"
    }

    public class Visitor : RulebookAnyCallVisitor() {
        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)
            process(node.parameters.asList(), node.lineNumber, node.code.lineNumber)
        }

        override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
            process(
                (node.arguments as? ArgumentListExpression)?.expressions ?: return,
                node.lineNumber,
                node.lastLineNumber,
            )
        }

        private fun process(parameters: List<ASTNode>, start: Int, end: Int) {
            val (firstParameter, lastParameter) =
                parameters
                    .takeUnless { it.isEmpty() }
                    ?.let { it.first() to it.last() }
                    ?.takeIf {
                        it.first.lineNumber > -1 &&
                            it.second.lineNumber > -1 &&
                            it.second !is ClosureExpression
                    } ?: return
            var lineNumber = firstParameter.lineNumber
            var lastLineNumber = lastParameter.lastLineNumber
            while (lineNumber > start) {
                if (sourceCode.line(lineNumber - 1).isBlank()) {
                    violations += rule.createViolation(lineNumber, "", Messages[MSG_FIRST])
                }
                lineNumber--
            }
            while (lastLineNumber < end) {
                if (sourceCode.line(lastLineNumber - 1).isBlank()) {
                    violations += rule.createViolation(lastLineNumber, "", Messages[MSG_LAST])
                }
                lastLineNumber++
            }
        }
    }
}
