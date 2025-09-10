package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.visitors.RulebookAnyCallVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MethodCall

/** [See detail](https://hanggrian.github.io/rulebook/rules/#parameter-wrap) */
public class ParameterWrapRule : RulebookAstRule() {
    override fun getName(): String = "ParameterWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "parameter.wrap"
    }

    public class Visitor : RulebookAnyCallVisitor() {
        override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
            process((node.arguments as? ArgumentListExpression)?.expressions ?: return)
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
                .takeIf { it.isNotEmpty() && it.last().lineNumber > it.first().lineNumber }
                ?: return

            // checks for violation
            for ((i, parameter) in parameters.withIndex().drop(1)) {
                parameters[i - 1]
                    .lastLineNumber
                    .takeIf { it == parameter.lineNumber }
                    ?: continue
                addViolation(parameter, Messages[MSG])
            }
        }
    }
}
