package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasJumpStatement
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening) */
public class IfElseFlatteningRule : RulebookRule() {
    override fun getName(): String = "IfElseFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_INVERT = "if.else.flattening.invert"
        const val MSG_LIFT = "if.else.flattening.lift"

        private fun Statement.hasMultipleLines() =
            (this as? BlockStatement)
                ?.statements
                ?.let { it.singleOrNull()?.isMultiline() ?: (it.size > 1) }
                ?: false
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            super.visitBlockStatement(node)

            // get last if
            val `if` =
                node.statements.lastOrNull() as? IfStatement
                    ?: return

            // checks for violation
            val `else` = `if`.elseBlock
            if (!`else`.isEmpty) {
                `else`.takeUnless { it is IfStatement } ?: return
                addViolation(`else`, Messages[MSG_LIFT])
                return
            }
            `if`
                .takeUnless { it.hasJumpStatement() }
                ?.ifBlock
                ?.takeIf { it.hasMultipleLines() }
                ?: return
            addViolation(`if`, Messages[MSG_INVERT])
        }
    }
}
