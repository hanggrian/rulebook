package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#parameter-wrap) */
public class ParameterWrapRule : RulebookAstRule() {
    override fun getName(): String = "ParameterWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_ARGUMENT = "parameter.wrap.argument"
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
            val arguments = node.arguments as? ArgumentListExpression ?: return
            process(arguments.expressions)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)
            process(node.parameters.asList())
        }

        override fun visitListExpression(node: ListExpression) {
            super.visitListExpression(node)
            process(node.expressions)
        }

        override fun visitMapExpression(node: MapExpression) {
            super.visitMapExpression(node)
            process(node.mapEntryExpressions)
        }

        private fun process(parameters: List<ASTNode>) {
            // target multiline parameters
            parameters
                .takeIf { it.isNotEmpty() && it.last().lineNumber > parameters.first().lineNumber }
                ?: return

            // checks for violation
            for ((i, parameter) in parameters.withIndex().drop(1)) {
                parameters[i - 1]
                    .takeIf { it.lastLineNumber == parameter.lineNumber }
                    ?: continue
                addViolation(parameter, Messages[MSG_ARGUMENT])
            }
        }
    }
}
