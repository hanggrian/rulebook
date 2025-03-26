package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codehaus.groovy.ast.stmt.ThrowStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening) */
public class DefaultFlatteningRule : RulebookRule() {
    override fun getName(): String = "DefaultFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "default.flattening"

        private fun Statement.isJumpStatementExceptBreak() =
            this is ReturnStatement ||
                this is ThrowStatement ||
                this is ContinueStatement
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            super.visitSwitch(node)

            // skip no default
            val default = node.defaultStatement ?: return

            // checks for violation
            node
                .caseStatements
                .takeIf { cases2 ->
                    cases2.all { c ->
                        if (c.code.isJumpStatementExceptBreak()) {
                            return@all true
                        }
                        (c.code as? BlockStatement)
                            ?.statements
                            .orEmpty()
                            .any { it.isJumpStatementExceptBreak() }
                    }
                } ?: return
            addViolation(default, Messages[MSG])
        }
    }
}
