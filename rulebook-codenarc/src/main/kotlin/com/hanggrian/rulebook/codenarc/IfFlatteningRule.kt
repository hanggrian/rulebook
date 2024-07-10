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
            // only proceed on one if and no else
            val if2 =
                node.statements
                    .singleOrNull()
                    ?.takeIf { it is IfStatement }
                    ?.takeIf { (it as IfStatement).elseBlock.isEmpty } as IfStatement?
                    ?: return super.visitBlockStatement(node)

            // checks for violation
            if2.ifBlock.text.takeIf { ';' in it } ?: return super.visitBlockStatement(node)
            addViolation(if2, Messages[MSG])

            super.visitBlockStatement(node)
        }
    }
}
