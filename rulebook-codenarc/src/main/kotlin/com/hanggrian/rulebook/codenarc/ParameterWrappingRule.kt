package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#parameter-wrapping) */
public class ParameterWrappingRule : RulebookRule() {
    override fun getName(): String = "ParameterWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_ARGUMENT = "parameter.wrapping.argument"
    }

    public class Visitor : AbstractAstVisitor() {
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

        override fun visitConstructorCallExpression(node: ConstructorCallExpression) {
            super.visitConstructorCallExpression(node)

            // target multiline parameters
            val arguments =
                node
                    .takeIf { it.isMultiline() }
                    ?.arguments
                    ?: return

            // checks for violation
            process((arguments as? ArgumentListExpression)?.expressions ?: return)
        }

        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)

            // target multiline parameters
            val arguments =
                node
                    .takeIf { it.isMultiline() }
                    ?.arguments
                    ?: return

            // checks for violation
            process((arguments as? ArgumentListExpression)?.expressions ?: return)
        }

        override fun visitStaticMethodCallExpression(node: StaticMethodCallExpression) {
            super.visitStaticMethodCallExpression(node)

            // target multiline parameters
            val arguments =
                node
                    .takeIf { it.isMultiline() }
                    ?.arguments
                    ?: return

            // checks for violation
            process((arguments as? ArgumentListExpression)?.expressions ?: return)
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
