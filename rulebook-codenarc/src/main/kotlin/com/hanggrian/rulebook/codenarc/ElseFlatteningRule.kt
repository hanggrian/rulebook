package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening)
 */
public class ElseFlatteningRule : Rule() {
    override fun getName(): String = "ElseFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "else.flattening"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            // checks for violation
            val `if` =
                node.statements.lastOrNull() as? IfStatement
                    ?: return super.visitBlockStatement(node)
            val `else` = `if`.elseBlock
            (`if`.ifBlock as? BlockStatement)
                .takeIf { `else` !is IfStatement }
                ?.statements
                ?.takeIf { s -> s.any { it is ReturnStatement || it is ThrowStatement } }
                ?: return super.visitBlockStatement(node)
            addViolation(`else`, Messages[MSG])

            super.visitBlockStatement(node)
        }
    }
}
