package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#if-statement-nesting).
 */
public class IfStatementNestingRule : RulebookRule() {
    override fun getName(): String = "IfStatementNesting"

    override fun getAstVisitorClass(): Class<*> = IfStatementNestingVisitor::class.java
}

public class IfStatementNestingVisitor : AbstractAstVisitor() {
    override fun visitBlockStatement(statement: BlockStatement) {
        // only proceed on one if and no else
        val if2 =
            statement.statements.singleOrNull()
                ?.takeIf { (it as? IfStatement)?.elseBlock?.isEmpty ?: false } as IfStatement?
                ?: return super.visitBlockStatement(statement)

        // report 2 lines content
        if2.ifBlock.text.takeIf { ';' in it } ?: return super.visitBlockStatement(statement)
        addViolation(if2, Messages[MSG])

        super.visitBlockStatement(statement)
    }

    internal companion object {
        const val MSG = "if.statement.nesting"
    }
}
