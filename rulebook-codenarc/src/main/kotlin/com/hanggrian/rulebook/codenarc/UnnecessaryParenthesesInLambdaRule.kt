package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.expr.LambdaExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-parentheses-in-lambda) */
public class UnnecessaryParenthesesInLambdaRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessaryParenthesesInLambda"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "unnecessary.parentheses.in.lambda"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitLambdaExpression(node: LambdaExpression) {
            super.visitLambdaExpression(node)

            // no multiple parameters
            val parameter =
                node
                    .parameters
                    ?.singleOrNull()
                    ?: return

            // checks for violation
            sourceLine(parameter)
                .takeIf { ") ->" in it && parameter.isDynamicTyped }
                ?: return
            addViolation(parameter, Messages[MSG])
        }
    }
}
