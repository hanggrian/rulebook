package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#if-statement-flattening)
 */
public class IfStatementFlatteningRule : Rule() {
    override fun getName(): String = "IfStatementFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            // only proceed on one if and no else
            val if2 =
                node.statements.singleOrNull()
                    ?.takeIf { it is IfStatement }
                    ?.takeIf { (it as IfStatement).elseBlock.isEmpty } as IfStatement?
                    ?: return super.visitBlockStatement(node)

            // checks for violation
            if2.ifBlock.text.takeIf { ';' in it } ?: return super.visitBlockStatement(node)
            addViolation(if2, Messages[MSG])

            super.visitBlockStatement(node)
        }
    }

    internal companion object {
        const val MSG = "if.statement.flattening"
    }
}
