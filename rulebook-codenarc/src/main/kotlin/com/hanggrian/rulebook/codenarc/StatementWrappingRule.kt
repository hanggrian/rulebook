package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#statement-wrapping) */
public class StatementWrappingRule : RulebookRule() {
    override fun getName(): String = "StatementWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "statement.wrapping"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitIfElse(node: IfStatement) {
            super.visitIfElse(node)
            processControlFlow(node.ifBlock as? BlockStatement ?: return)
        }

        override fun visitForLoop(node: ForStatement) {
            super.visitForLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitWhileLoop(node: WhileStatement) {
            super.visitWhileLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitDoWhileLoop(node: DoWhileStatement) {
            super.visitDoWhileLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitBlockStatement(node: BlockStatement) {
            super.visitBlockStatement(node)

            for (statement in node.statements) {
                // checks for violation
                sourceLine(statement)
                    .takeIf { s ->
                        ';' in s &&
                            "for" !in s &&
                            s.substringAfter(';').let { it.isNotEmpty() && "//" !in it }
                    } ?: continue
                addViolation(statement, Messages.get(MSG, ';'))
            }
        }

        private fun processControlFlow(blockStatement: BlockStatement) {
            val statement =
                blockStatement
                    .statements
                    ?.singleOrNull()
                    ?: return
            sourceLine(statement)
                .takeIf { '{' in it && '}' in it }
                ?: return
            addViolation(statement, Messages.get(MSG, '{'))
        }
    }
}
