package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoLonelyCaseRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.EmptyStatement
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-lonely-case) */
public class NoLonelyCaseRule : RulebookAstRule() {
    override fun getName(): String = "NoLonelyCase"

    override fun getAstVisitorClass(): Class<*> = NoLonelyCaseVisitor::class.java

    internal companion object {
        const val MSG = "no.lonely.case"
    }
}

public class NoLonelyCaseVisitor : RulebookVisitor() {
    override fun visitSwitch(node: SwitchStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        // skip multiple branches
        (node.caseStatements + node.defaultStatement.takeUnless { it is EmptyStatement })
            .filterNotNull()
            .singleOrNull()
            ?: return super.visitSwitch(node)
        addViolation(node, Messages[MSG])

        super.visitSwitch(node)
    }
}
