package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-case-branching)
 */
public class SwitchCaseBranchingRule : Rule() {
    override fun getName(): String = "SwitchCaseBranching"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "switch.case.branching"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            super.visitSwitch(node)

            // checks for violation
            node
                .caseStatements
                .takeIf { it.size == 1 }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
