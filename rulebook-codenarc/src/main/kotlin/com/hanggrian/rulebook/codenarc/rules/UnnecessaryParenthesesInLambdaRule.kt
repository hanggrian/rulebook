package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.expr.LambdaExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-parentheses-in-lambda) */
public class UnnecessaryParenthesesInLambdaRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessaryParenthesesInLambda"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "unnecessary.parentheses.in.lambda"
    }

    public class Visitor : RulebookVisitor() {
        override fun visitLambdaExpression(node: LambdaExpression) {
            super.visitLambdaExpression(node)

            // skip multiple parameters
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
