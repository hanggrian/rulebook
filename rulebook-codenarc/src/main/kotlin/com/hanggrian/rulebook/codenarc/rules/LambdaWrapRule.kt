package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.firstStatement
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.expr.ClosureExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lambda-wrap) */
public class LambdaWrapRule : RulebookAstRule() {
    override fun getName(): String = "LambdaWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "lambda.wrap"
    }

    public class Visitor : RulebookVisitor() {
        override fun visitClosureExpression(node: ClosureExpression) {
            super.visitClosureExpression(node)

            // target multiline lambda
            val expression =
                node
                    .code
                    .firstStatement()
                    .takeIf { it.isMultiline() }
                    ?: return
            val parameters =
                node.parameters?.firstOrNull()
                    ?: node

            // checks for violation
            expression
                .lineNumber
                .takeIf { it == parameters.lastLineNumber }
                ?: return
            addViolation(expression, Messages[MSG])
        }
    }
}
