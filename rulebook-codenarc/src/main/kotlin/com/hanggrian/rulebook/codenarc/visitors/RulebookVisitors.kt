package com.hanggrian.rulebook.codenarc.visitors

import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** An alias to unconfigured visitor. */
public typealias RulebookVisitor = AbstractAstVisitor

/** Visitor that captures any method call with a single function. */
public abstract class RulebookAnyCallVisitor : RulebookVisitor() {
    final override fun visitConstructorCallExpression(node: ConstructorCallExpression) {
        super.visitConstructorCallExpression(node)
        visitAnyCallExpression(node)
    }

    final override fun visitMethodCallExpression(node: MethodCallExpression) {
        super.visitMethodCallExpression(node)
        visitAnyCallExpression(node)
    }

    final override fun visitStaticMethodCallExpression(node: StaticMethodCallExpression) {
        super.visitStaticMethodCallExpression(node)
        visitAnyCallExpression(node)
    }

    public abstract fun <T> visitAnyCallExpression(node: T) where T : Expression, T : MethodCall
}
