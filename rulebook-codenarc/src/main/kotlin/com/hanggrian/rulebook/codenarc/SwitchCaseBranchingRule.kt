package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#switch-case-branching) */
public class SwitchCaseBranchingRule : RulebookRule() {
    override fun getName(): String = "SwitchCaseBranching"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "switch.case.branching"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            super.visitSwitch(node)

            // target single entry
            val case =
                node
                    .caseStatements
                    .singleOrNull()
                    ?: return

            // checks for violation
            (case.code as? BlockStatement)
                ?.statements
                ?.takeUnless { it.last() is BreakStatement }
            addViolation(node, Messages[MSG])
        }
    }
}
