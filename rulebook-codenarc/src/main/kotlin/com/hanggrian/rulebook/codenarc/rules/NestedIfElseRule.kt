package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#nested-if-else) */
public class NestedIfElseRule : RulebookAstRule() {
    override fun getName(): String = "NestedIfElse"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_INVERT = "nested.if.else.invert"
        const val MSG_LIFT = "nested.if.else.lift"

        fun Statement.hasMultipleLines() =
            (this as? BlockStatement)
                ?.statements
                .orEmpty()
                .let { it.singleOrNull()?.isMultiline() ?: (it.size > 1) }
    }

    public class Visitor : RulebookVisitor() {
        override fun visitForLoop(node: ForStatement) {
            super.visitForLoop(node)
            process(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitDoWhileLoop(node: DoWhileStatement) {
            super.visitDoWhileLoop(node)
            process(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)
            process(node.code as? BlockStatement ?: return)
        }

        private fun process(node: BlockStatement) {
            // get last if
            val `if` =
                node.statements.lastOrNull()
                    as? IfStatement
                    ?: return

            // checks for violation
            val `else` = `if`.elseBlock
            if (!`else`.isEmpty) {
                `else`
                    .takeUnless { it is IfStatement }
                    ?: return
                addViolation(`else`, Messages[MSG_LIFT])
                return
            }
            `if`
                .ifBlock
                ?.takeIf { !it.hasJumpStatement() && it.hasMultipleLines() }
                ?: return
            addViolation(`if`, Messages[MSG_INVERT])
        }
    }
}
