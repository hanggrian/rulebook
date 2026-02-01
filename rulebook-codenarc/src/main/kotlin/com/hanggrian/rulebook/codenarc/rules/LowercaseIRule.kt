package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.rules.LowercaseIRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-i) */
public class LowercaseIRule : RulebookAstRule() {
    override fun getName(): String = "LowercaseI"

    override fun getAstVisitorClass(): Class<*> = LowercaseIVisitor::class.java

    internal companion object {
        const val MSG = "lowercase.i"
    }
}

public class LowercaseIVisitor : RulebookVisitor() {
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
