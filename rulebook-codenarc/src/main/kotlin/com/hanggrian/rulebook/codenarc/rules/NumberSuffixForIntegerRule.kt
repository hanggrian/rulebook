package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-integer) */
public class NumberSuffixForIntegerRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForInteger"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "number.suffix.for.integer"
    }

    public class Visitor : RulebookVisitor() {
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
                ?.last()
                ?.takeIf { it == 'I' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
