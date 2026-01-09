package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.rules.RedundantDefaultRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-default) */
public class RedundantDefaultRule : RulebookAstRule() {
    override fun getName(): String = "RedundantDefault"

    override fun getAstVisitorClass(): Class<*> = RedundantDefaultVisitor::class.java

    internal companion object {
        const val MSG = "redundant.default"
    }
}

public class RedundantDefaultVisitor : RulebookVisitor() {
    override fun visitSwitch(node: SwitchStatement) {
        super.visitSwitch(node)

        // find default
        val default = node.defaultStatement ?: return

        // checks for violation
        node
            .caseStatements
            .takeIf { cases2 -> cases2.all { it.code.hasJumpStatement(false) } }
            ?: return
        addViolation(default, Messages[MSG])
    }
}
