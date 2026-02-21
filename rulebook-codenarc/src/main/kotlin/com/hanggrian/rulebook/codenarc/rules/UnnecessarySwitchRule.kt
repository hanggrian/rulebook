package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.UnnecessarySwitchRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-switch) */
public class UnnecessarySwitchRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessarySwitch"

    override fun getAstVisitorClass(): Class<*> = UnnecessarySwitchVisitor::class.java

    internal companion object {
        const val MSG = "unnecessary.switch"
    }
}

public class UnnecessarySwitchVisitor : RulebookVisitor() {
    override fun visitSwitch(node: SwitchStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip multiple branches
        val case =
            node
                .caseStatements
                .singleOrNull()
                ?: return super.visitSwitch(node)

        // checks for violation
        (case.code as? BlockStatement)
            ?.statements
            ?.last()
            ?.takeUnless { it is BreakStatement }
        addViolation(node, Messages[MSG])

        super.visitSwitch(node)
    }
}
