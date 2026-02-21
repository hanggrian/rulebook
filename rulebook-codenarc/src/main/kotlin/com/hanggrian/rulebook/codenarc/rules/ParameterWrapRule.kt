package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.ParameterWrapRule.Companion.MSG
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

    override fun getAstVisitorClass(): Class<*> = ParameterWrapVisitor::class.java

    internal companion object {
        const val MSG = "parameter.wrap"
    }
}

public class ParameterWrapVisitor : RulebookAnyCallVisitor() {
    override fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall {
        if (!isFirstVisit(node)) {
            return
        }
        process((node.arguments as? ArgumentListExpression)?.expressions ?: return)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.parameters.asList())
        super.visitConstructorOrMethod(node, isConstructor)
    }

    override fun visitListExpression(node: ListExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.expressions)
        super.visitListExpression(node)
    }

    override fun visitMapExpression(node: MapExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.mapEntryExpressions)
        super.visitMapExpression(node)
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
