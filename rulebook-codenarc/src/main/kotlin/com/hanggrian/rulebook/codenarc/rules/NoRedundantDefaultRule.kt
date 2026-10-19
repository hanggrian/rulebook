package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.rules.NoRedundantDefaultRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-redundant-default) */
public class NoRedundantDefaultRule : RulebookAstRule() {
    override fun getName(): String = "NoRedundantDefault"

    override fun getAstVisitorClass(): Class<*> = NoRedundantDefaultVisitor::class.java

    internal companion object {
        const val MSG = "no.redundant.default"
    }
}

public class NoRedundantDefaultVisitor : RulebookVisitor() {
    override fun visitSwitch(node: SwitchStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        // find default
        val default = node.defaultStatement ?: return super.visitSwitch(node)

        // checks for violation
        node
            .caseStatements
            .takeIf { cases2 ->
                cases2.isNotEmpty() &&
                    cases2.all { it.code.hasJumpStatement(false) }
            } ?: return super.visitSwitch(node)
        addViolation(default, Messages[MSG])

        super.visitSwitch(node)
    }
}
