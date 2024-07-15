package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasReturnOrThrow
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening)
 */
public class DefaultFlatteningRule : Rule() {
    override fun getName(): String = "DefaultFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "default.flattening"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            // skip no default
            val default = node.defaultStatement ?: return

            // checks for violation
            node
                .caseStatements
                .takeIf { case -> case.all { it.hasReturnOrThrow() } }
                ?: return
            addViolation(default, Messages[MSG])

            super.visitSwitch(node)
        }
    }
}
