package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.DecentralizedDependencyRule.Companion.MSG
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.GStringExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#decentralized-dependency) */
public class DecentralizedDependencyRule : RulebookAstRule() {
    override fun getName(): String = "DecentralizedDependency"

    override fun getAstVisitorClass(): Class<*> = DecentralizedDependencyVisitor::class.java

    internal companion object {
        const val MSG = "decentralized.dependency"
    }
}

public class DecentralizedDependencyVisitor : RulebookVisitor() {
    private val expressions = mutableListOf<MethodCallExpression>()

    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (isFirstVisit(node) && isScript()) {
            expressions += node
        }
        super.visitMethodCallExpression(node)
    }

    override fun visitClassComplete(node: ClassNode) {
        if (!isFirstVisit(node) && isScript()) {
            return
        }

        val domainBlocks =
            expressions.filter { expr ->
                (expr.objectExpression as? VariableExpression)?.name == "this"
            }

        for (expression in expressions) {
            expression
                .takeUnless { it.methodAsString == "project" }
                ?: continue

            // collect callee
            val callee: String =
                (expression.objectExpression as? VariableExpression)
                    ?.name
                    ?.takeUnless { it == "this" }
                    ?: domainBlocks
                        .firstOrNull { block ->
                            block != expression &&
                                expression.lineNumber >= block.lineNumber &&
                                expression.lineNumber <= block.lastLineNumber
                        }?.methodAsString
                    ?: continue
            callee
                .takeIf { it == "dependencies" }
                ?: return

            // checks for violation
            val argument =
                (expression.arguments as? ArgumentListExpression)
                    ?.expressions
                    ?.singleOrNull()
                    ?.takeIf {
                        it is GStringExpression ||
                            (it is ConstantExpression && it.value is String)
                    } ?: continue
            println(argument)
            addViolation(argument, Messages[MSG])
        }

        super.visitClassComplete(node)
    }
}
