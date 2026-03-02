package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.RedundantEqualityRule.Companion.MSG
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-equality) */
public class RedundantEqualityRule : RulebookAstRule() {
    override fun getName(): String = "RedundantEquality"

    override fun getAstVisitorClass(): Class<*> = RedundantEqualityVisitor::class.java

    internal companion object {
        const val MSG = "redundant.equality"
    }
}

public class RedundantEqualityVisitor : RulebookVisitor() {
    override fun visitBinaryExpression(node: BinaryExpression) {
        if (!isFirstVisit(node)) {
            return
        }

        // find constants
        node.leftExpression.takeIf { it is ConstantExpression }
            ?: node.rightExpression.takeIf { it is ConstantExpression }
            ?: return super.visitBinaryExpression(node)

        // checks for violation
        node
            .operation
            .takeIf { it.text == "==" || it.text == "!=" }
            ?: return
        addViolation(node, Messages[MSG])

        super.visitBinaryExpression(node)
    }
}
