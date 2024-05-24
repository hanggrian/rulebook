package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#qualifier-consistency)
 */
public class QualifierConsistencyRule : RulebookRule() {
    override fun getName(): String = "QualifierConsistency"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    public class Visitor : AbstractAstVisitor() {
        private val importPaths = mutableSetOf<String>()

        override fun visitImports(node: ModuleNode) {
            // keep import list
            node.imports.forEach { importPaths += it.type.name }

            super.visitImports(node)
        }

        override fun visitField(node: FieldNode) {
            // checks for violation
            process(node, node.type.name)

            super.visitField(node)
        }

        override fun visitMethodEx(node: MethodNode) {
            // checks for violation
            node.parameters.forEach { process(it, it.type.name) }

            super.visitMethodEx(node)
        }

        override fun visitMethodCallExpression(call: MethodCallExpression) {
            // checks for violation
            val expression = call.objectExpression
            process(expression, expression.text)

            return super.visitMethodCallExpression(call)
        }

        private fun process(node: ASTNode, text: String) {
            if (text !in importPaths) {
                return
            }
            addViolation(node, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "qualifier.consistency"
    }
}
