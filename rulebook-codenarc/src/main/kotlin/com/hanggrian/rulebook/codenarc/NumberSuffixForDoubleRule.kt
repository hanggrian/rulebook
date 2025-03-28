package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.literalOf
import org.codehaus.groovy.ast.ClassHelper.double_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#number-suffix-for-double) */
public class NumberSuffixForDoubleRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForDouble"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "number.suffix.for.double"
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
                .takeIf { it == double_TYPE }
                ?: return

            // checks for violation
            literalOf(node)
                ?.last()
                ?.takeIf { it == 'D' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
