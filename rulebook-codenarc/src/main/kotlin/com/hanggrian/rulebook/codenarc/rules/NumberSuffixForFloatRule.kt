package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-float) */
public class NumberSuffixForFloatRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForFloat"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "number.suffix.for.float"
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
                .takeIf { it == float_TYPE }
                ?: return

            // checks for violation
            getLiteral(node)
                ?.last()
                ?.takeIf { it == 'F' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
