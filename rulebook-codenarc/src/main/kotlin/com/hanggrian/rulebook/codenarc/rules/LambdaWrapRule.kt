package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.firstStatement
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.LambdaWrapRule.Companion.MSG
import org.codehaus.groovy.ast.expr.ClosureExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lambda-wrap) */
public class LambdaWrapRule : RulebookAstRule() {
    override fun getName(): String = "LambdaWrap"

    override fun getAstVisitorClass(): Class<*> = LambdaWrapVisitor::class.java

    internal companion object {
        const val MSG = "lambda.wrap"
    }
}

public class LambdaWrapVisitor : RulebookVisitor() {
    override fun visitClosureExpression(node: ClosureExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // target multiline lambda
        val expression =
            node
                .code
                .firstStatement()
                .takeIf { it.isMultiline() }
                ?: return super.visitClosureExpression(node)
        val parameters =
            node.parameters?.firstOrNull()
                ?: node

        // checks for violation
        expression
            .lineNumber
            .takeIf { it == parameters.lastLineNumber }
            ?: return super.visitClosureExpression(node)
        addViolation(expression, Messages[MSG])

        super.visitClosureExpression(node)
    }
}
