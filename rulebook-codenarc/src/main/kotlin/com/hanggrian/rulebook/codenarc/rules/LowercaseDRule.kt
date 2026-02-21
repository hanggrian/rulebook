package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.rules.LowercaseDRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper.double_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-d) */
public class LowercaseDRule : RulebookAstRule() {
    override fun getName(): String = "LowercaseD"

    override fun getAstVisitorClass(): Class<*> = LowercaseDVisitor::class.java

    internal companion object {
        const val MSG = "lowercase.d"
    }
}

public class LowercaseDVisitor : RulebookVisitor() {
    override fun visitConstantExpression(node: ConstantExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip other literals
        node
            .type
            .takeIf { it == double_TYPE }
            ?: return super.visitConstantExpression(node)

        // checks for violation
        getLiteral(node)
            ?.last()
            ?.takeIf { it == 'D' }
            ?: return super.visitConstantExpression(node)
        addViolation(node, Messages[MSG])

        super.visitConstantExpression(node)
    }
}
