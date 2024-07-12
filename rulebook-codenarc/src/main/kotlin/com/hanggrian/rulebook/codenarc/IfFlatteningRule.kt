package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#if-flattening)
 */
public class IfFlatteningRule : Rule() {
    override fun getName(): String = "IfFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "if.flattening"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            // checks for violation
            val `if` =
                node.statements.lastOrNull() as? IfStatement
                    ?: return super.visitBlockStatement(node)
            `if`.ifBlock
                ?.takeIf { `if`.elseBlock.isEmpty }
                ?.text
                ?.takeIf { ';' in it }
                ?: return super.visitBlockStatement(node)
            addViolation(`if`, Messages[MSG])

            super.visitBlockStatement(node)
        }
    }
}
