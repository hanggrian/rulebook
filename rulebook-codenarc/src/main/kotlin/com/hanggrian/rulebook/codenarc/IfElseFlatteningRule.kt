package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
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
            (`if`.ifBlock as? BlockStatement)
                ?.statements
                ?.takeIf { it.singleOrNull()?.isMultiline() ?: (it.size > 1) }
                ?: return
            addViolation(`if`, Messages[MSG_INVERT])
        }
    }
}
