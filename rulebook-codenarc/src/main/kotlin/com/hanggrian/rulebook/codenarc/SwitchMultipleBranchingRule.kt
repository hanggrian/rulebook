package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-multiple-branching)
 */
public class SwitchMultipleBranchingRule : Rule() {
    override fun getName(): String = "SwitchMultipleBranching"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "switch.multiple.branching"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(statement: SwitchStatement) {
            // checks for violation
            statement.caseStatements.takeIf { it.size < 2 } ?: return
            addViolation(statement, Messages[MSG])

            super.visitSwitch(statement)
        }
    }
}
