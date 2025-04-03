package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-qualifier) */
public class RedundantQualifierRule : RulebookAstRule() {
    override fun getName(): String = "RedundantQualifier"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "redundant.qualifier"
    }

    // TODO capturing `ConstructorCallExpression` is disabled because
    //   `visitConstructorCallExpression` is called twice, find out why
    public class Visitor : AbstractAstVisitor() {
        private val importPaths = mutableSetOf<String>()
        private val targetNodes = mutableSetOf<ASTNode>()

        override fun visitImports(node: ModuleNode) {
            super.visitImports(node)

            // keep import list
            node.imports.forEach { importPaths += it.type.name }
        }

        override fun visitField(node: FieldNode) {
            super.visitField(node)

            // checks for violation
            process(node, node.type.name)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)

            // checks for violation
            process(node, node.returnType.name)
            node.parameters.forEach { process(it, it.type.name) }
        }

        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)

            // checks for violation
            val `class` = node.objectExpression
            process(`class`, `class`.text)
            process(`class`, "${`class`.text}.${node.methodAsString}")
        }

        private fun process(node: ASTNode, text: String) {
            node
                .takeIf { text in importPaths }
                ?: return
            if (!targetNodes.add(node)) {
                return
            }
            addViolation(
                node,
                Messages.get(
                    MSG,
                    when {
                        '.' in text -> text.substringBeforeLast('.') + '.'
                        else -> text
                    },
                ),
            )
        }
    }
}
