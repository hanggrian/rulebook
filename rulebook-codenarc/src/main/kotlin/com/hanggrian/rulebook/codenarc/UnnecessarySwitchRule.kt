package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#unnecessary-switch) */
public class UnnecessarySwitchRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessarySwitch"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "unnecessary.switch"
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
