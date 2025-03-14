package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.literalOf
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#float-suffix-lowercasing) */
public class FloatSuffixLowercasingRule : RulebookRule() {
    override fun getName(): String = "FloatSuffixLowercasing"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "float.suffix.lowercasing"
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
            literalOf(node)
                ?.last()
                ?.takeIf { it == 'F' }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
