package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.literalOf
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#integer-suffix-lowercasing) */
public class IntegerSuffixLowercasingRule : RulebookRule() {
    override fun getName(): String = "IntegerSuffixLowercasing"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "integer.suffix.lowercasing"
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
            literalOf(node)
                ?.singleOrNull { it.isLetter() }
                ?.takeIf { it == 'I' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
