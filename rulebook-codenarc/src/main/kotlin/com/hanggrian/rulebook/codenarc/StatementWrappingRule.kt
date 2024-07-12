package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#statement-wrapping)
 */
public class StatementWrappingRule : Rule() {
    override fun getName(): String = "StatementWrapping"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "statement.wrapping"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(statement: BlockStatement) {
            for (statement2 in statement.statements) {
                // checks for violation
                sourceLine(statement2)
                    .takeIf { line ->
                        ';' in line &&
                            line.substringAfter(';').let { it.isNotEmpty() && "//" !in it }
                    } ?: continue
                addViolation(statement2, Messages[MSG])
            }

            super.visitBlockStatement(statement)
        }
    }
}
