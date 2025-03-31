package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.getLiteral
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#number-suffix-for-float) */
public class NumberSuffixForFloatRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForFloat"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "number.suffix.for.float"
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
