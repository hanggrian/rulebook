package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#single-statement-declaration)
 */
public class SingleStatementDeclarationRule : Rule() {
    override fun getName(): String = "SingleStatementDeclaration"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "single.statement.declaration"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(statement: BlockStatement) {
            for (statement2 in statement.statements) {
                // checks for violation
                sourceLine(statement2).takeIf { ';' in it } ?: continue
                addViolation(statement2, Messages[MSG])
            }

            super.visitBlockStatement(statement)
        }
    }
}
