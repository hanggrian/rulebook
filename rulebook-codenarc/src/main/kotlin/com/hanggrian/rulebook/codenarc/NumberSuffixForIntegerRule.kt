package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.getLiteral
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-integer) */
public class NumberSuffixForIntegerRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForInteger"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "number.suffix.for.integer"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitConstantExpression(node: ConstantExpression) {
            super.visitConstantExpression(node)
            if (!isFirstVisit(node)) {
                return
            }

            // skip other literals
            node
                .type
                .takeIf { it == int_TYPE }
                ?: return

            // checks for violation
            getLiteral(node)
                ?.singleOrNull { it.isLetter() }
                ?.takeIf { it == 'I' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
