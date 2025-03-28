package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#parameter-wrap) */
public class ParameterWrapRule : RulebookAstRule() {
    override fun getName(): String = "ParameterWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_ARGUMENT = "parameter.wrap.argument"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitConstructorCallExpression(node: ConstructorCallExpression) {
            super.visitConstructorCallExpression(node)
            visitCallExpression(node)
        }

        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)
            visitCallExpression(node)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)

            // target multiline parameters
            val parameters =
                node
                    .parameters
                    ?.takeIf { it.isNotEmpty() && it.last().lineNumber > node.lineNumber }
                    ?: return

            // checks for violation
            process(parameters.asList())
        }

        private fun <T> visitCallExpression(node: T) where T : Expression, T : MethodCall {
            // target multiline parameters
            val arguments =
                node
                    .takeIf { it.isMultiline() }
                    ?.arguments
                    as? ArgumentListExpression
                    ?: return

            // checks for violation
            process(arguments.expressions)
        }

        private fun process(parameters: List<ASTNode>) {
            for ((i, parameter) in parameters.withIndex().drop(1)) {
                parameters[i - 1]
                    .takeUnless { it.lastLineNumber + 1 == parameter.lineNumber }
                    ?: continue
                addViolation(parameter, Messages[MSG_ARGUMENT])
            }
        }
    }
}
