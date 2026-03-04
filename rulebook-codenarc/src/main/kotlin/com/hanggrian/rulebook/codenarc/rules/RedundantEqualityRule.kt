package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.RedundantEqualityRule.Companion.MSG
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassHelper.boolean_TYPE
import org.codehaus.groovy.ast.ClassHelper.double_TYPE
import org.codehaus.groovy.ast.ClassHelper.float_TYPE
import org.codehaus.groovy.ast.ClassHelper.int_TYPE
import org.codehaus.groovy.ast.ClassHelper.long_TYPE
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression

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
        node
            .takeIf { it.leftExpression.isConstant() || it.rightExpression.isConstant() }
            ?: return super.visitBinaryExpression(node)

        // checks for violation
        node
            .operation
            .takeIf { it.text == "==" || it.text == "!=" }
            ?: return super.visitBinaryExpression(node)
        addViolation(node, Messages[MSG])

        super.visitBinaryExpression(node)
    }

    private fun Expression.isConstant(): Boolean {
        if (this !is ConstantExpression) {
            return false
        }
        return isNullExpression ||
            type === int_TYPE ||
            type === long_TYPE ||
            type === boolean_TYPE ||
            type === double_TYPE ||
            type === float_TYPE
    }
}
