package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.NoNestedIfElseRule.Companion.MSG_INVERT
import com.hanggrian.rulebook.codenarc.rules.NoNestedIfElseRule.Companion.MSG_LIFT
import com.hanggrian.rulebook.codenarc.rules.NoNestedIfElseRule.Companion.hasMultipleLines
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.WhileStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-nested-if-else) */
public class NoNestedIfElseRule : RulebookAstRule() {
    override fun getName(): String = "NoNestedIfElse"

    override fun getAstVisitorClass(): Class<*> = NoNestedIfElseVisitor::class.java

    internal companion object {
        const val MSG_INVERT = "no.nested.if.else.invert"
        const val MSG_LIFT = "no.nested.if.else.lift"

        fun Statement.hasMultipleLines() =
            (this as? BlockStatement)
                ?.statements
                .orEmpty()
                .run { singleOrNull()?.isMultiline() ?: (size > 1) }
    }
}

public class NoNestedIfElseVisitor : RulebookVisitor() {
    override fun visitForLoop(node: ForStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock as? BlockStatement ?: return super.visitForLoop(node))
        super.visitForLoop(node)
    }

    override fun visitWhileLoop(node: WhileStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock as? BlockStatement ?: return super.visitWhileLoop(node))
        super.visitWhileLoop(node)
    }

    override fun visitDoWhileLoop(node: DoWhileStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock as? BlockStatement ?: return super.visitDoWhileLoop(node))
        super.visitDoWhileLoop(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }
        process(
            node.code as? BlockStatement
                ?: return super.visitConstructorOrMethod(node, isConstructor),
        )
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: BlockStatement) {
        // get last if
        val `if` =
            node.statements.lastOrNull()
                as? IfStatement
                ?: return

        // checks for violation
        `if`
            .elseBlock
            .takeUnless { it.isEmpty }
            ?.let { `else` ->
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
