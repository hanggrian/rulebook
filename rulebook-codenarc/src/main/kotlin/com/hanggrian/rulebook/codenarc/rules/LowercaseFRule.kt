package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.getLiteral
import com.hanggrian.rulebook.codenarc.rules.LowercaseFRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-f) */
public class LowercaseFRule : RulebookAstRule() {
    override fun getName(): String = "LowercaseF"

    override fun getAstVisitorClass(): Class<*> = LowercaseFVisitor::class.java

    internal companion object {
        const val MSG = "lowercase.f"
    }
}

public class LowercaseFVisitor : RulebookVisitor() {
    override fun visitConstantExpression(node: ConstantExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip other literals
        node
            .type
            .takeIf { it == float_TYPE }
            ?: return super.visitConstantExpression(node)

        // checks for violation
        getLiteral(node)
            ?.last()
            ?.takeIf { it == 'F' }
            ?: return super.visitConstantExpression(node)
        addViolation(node, Messages[MSG])

        super.visitConstantExpression(node)
    }
}
