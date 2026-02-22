package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.LowercaseHexRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lowercase-hex) */
public class LowercaseHexRule : RulebookAstRule() {
    override fun getName(): String = "LowercaseHex"

    override fun getAstVisitorClass(): Class<*> = LowercaseHexVisitor::class.java

    internal companion object {
        const val MSG = "lowercase.hex"
    }
}

public class LowercaseHexVisitor : RulebookVisitor() {
    override fun visitConstantExpression(node: ConstantExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip other literals
        node
            .type
            .takeIf { it == int_TYPE }
            ?: return super.visitConstantExpression(node)
        val value =
            sourceCode
                .lines[node.lineNumber - 1]
                .substring(node.columnNumber - 1, node.lastColumnNumber - 1)
                .takeIf { it.startsWith("0x", true) }
                ?: return super.visitConstantExpression(node)

        // checks for violation
        val valueReplacement =
            value
                .lowercase()
                .takeUnless { it == value }
                ?: return super.visitConstantExpression(node)
        addViolation(node, Messages[MSG, valueReplacement])

        super.visitConstantExpression(node)
    }
}
