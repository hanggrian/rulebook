package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.rules.NumberSuffixForDoubleRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper.double_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#number-suffix-for-double) */
public class NumberSuffixForDoubleRule : RulebookAstRule() {
    override fun getName(): String = "NumberSuffixForDouble"

    override fun getAstVisitorClass(): Class<*> = NumberSuffixForDoubleVisitor::class.java

    internal companion object {
        const val MSG = "number.suffix.for.double"
    }
}

public class NumberSuffixForDoubleVisitor : RulebookVisitor() {
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
        getLiteral(node)
            ?.last()
            ?.takeIf { it == 'D' }
            ?: return
        addViolation(node, Messages[MSG])
    }
}
