package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening)
 */
public class IfElseFlatteningRule : Rule() {
    override fun getName(): String = "IfElseFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitForLoop(node: ForStatement) {
            super.visitForLoop(node)
            process(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitWhileLoop(node: WhileStatement) {
            super.visitWhileLoop(node)
            process(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitMethodEx(node: MethodNode) {
            super.visitMethodEx(node)
            process(node.code as? BlockStatement ?: return)
        }

        private fun process(blockStatement: BlockStatement) {
            // get last if
            val `if` =
                blockStatement.statements.lastOrNull() as? IfStatement
                    ?: return

            // checks for violation
            val `else` = `if`.elseBlock
            if (!`else`.isEmpty) {
                `else`
                    .takeUnless { it is IfStatement }
                    ?.takeIf { it.hasMultipleLines() }
                    ?: return
                addViolation(`else`, Messages[MSG_LIFT])
                return
            }
            `if`
                .ifBlock
                .takeIf { it.hasMultipleLines() }
                ?: return
            addViolation(`if`, Messages[MSG_INVERT])
        }

        private companion object {
            fun Statement.hasMultipleLines() =
                (this as? BlockStatement)
                    ?.statements
                    ?.let { it.singleOrNull()?.isMultiline() ?: (it.size > 1) }
                    ?: false
        }
    }
}
