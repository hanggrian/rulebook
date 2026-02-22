package com.hanggrian.rulebook.codenarc.rules

import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCall
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractRule
import org.codenarc.rule.imports.AbstractImportRule

/** Rule that uses Groovy AST tree to validate a node. */
public abstract class RulebookAstRule : AbstractAstVisitorRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that validates an import statement. */
public abstract class RulebookImportRule : AbstractImportRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that checks source code by the raw file. */
public abstract class RulebookFileRule : AbstractRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** An alias to unconfigured visitor. */
public open class RulebookVisitor : AbstractAstVisitor() {
    public open fun isScript(): Boolean = sourceCode.name.endsWith(".gradle")
}

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
