package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.firstStatement
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lambda-wrap) */
public class LambdaWrapRule : RulebookAstRule() {
    override fun getName(): String = "LambdaWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "lambda.wrap"
    }

    public class Visitor : AbstractAstVisitor() {
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
