package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.firstStatement
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.LambdaWrapRule.Companion.MSG
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.LambdaExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lambda-wrap) */
public class LambdaWrapRule : RulebookAstRule() {
    override fun getName(): String = "LambdaWrap"

    override fun getAstVisitorClass(): Class<*> = LambdaWrapVisitor::class.java

    internal companion object {
        const val MSG = "lambda.wrap"
    }
}

public class LambdaWrapVisitor : RulebookVisitor() {
    override fun visitLambdaExpression(node: LambdaExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node)
        super.visitLambdaExpression(node)
    }

    override fun visitClosureExpression(node: ClosureExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node)
        super.visitClosureExpression(node)
    }

    private fun process(node: ClosureExpression) {
        // target multiline lambda
        val statement =
            node
                .code
                .takeIf { it.isMultiline() }
                ?.firstStatement()
                ?: return
        val parameter =
            node.parameters?.firstOrNull()
                ?: node

        // checks for violation
        statement
            .lineNumber
            .takeIf { it == parameter.lineNumber }
            ?: return
        addViolation(statement, Messages[MSG])
    }
}
