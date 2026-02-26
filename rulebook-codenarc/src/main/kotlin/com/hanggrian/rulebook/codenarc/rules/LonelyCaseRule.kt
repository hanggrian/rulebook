package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.LonelyCaseRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.BreakStatement
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-case) */
public class LonelyCaseRule : RulebookAstRule() {
    override fun getName(): String = "LonelyCase"

    override fun getAstVisitorClass(): Class<*> = LonelyCaseVisitor::class.java

    internal companion object {
        const val MSG = "lonely.case"
    }
}

public class LonelyCaseVisitor : RulebookVisitor() {
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
