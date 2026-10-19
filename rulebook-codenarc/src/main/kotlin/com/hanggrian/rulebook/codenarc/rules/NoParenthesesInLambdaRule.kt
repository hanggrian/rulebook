package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoParenthesesInLambdaRule.Companion.MSG
import org.codehaus.groovy.ast.expr.LambdaExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-parentheses-in-lambda) */
public class NoParenthesesInLambdaRule : RulebookAstRule() {
    override fun getName(): String = "NoParenthesesInLambda"

    override fun getAstVisitorClass(): Class<*> = NoParenthesesInLambdaVisitor::class.java

    internal companion object {
        const val MSG = "no.parentheses.in.lambda"
    }
}

public class NoParenthesesInLambdaVisitor : RulebookVisitor() {
    override fun visitLambdaExpression(node: LambdaExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip multiple parameters
        val parameter =
            node
                .parameters
                ?.singleOrNull()
                ?: return super.visitLambdaExpression(node)

        // checks for violation
        sourceLine(parameter)
            .takeIf { ") ->" in it && parameter.isDynamicTyped }
            ?: return super.visitLambdaExpression(node)
        addViolation(parameter, Messages[MSG])

        super.visitLambdaExpression(node)
    }
}
