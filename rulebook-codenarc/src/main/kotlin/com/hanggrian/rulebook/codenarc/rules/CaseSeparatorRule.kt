package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.getLineNumberBefore
import com.hanggrian.rulebook.codenarc.hasCommentAbove
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.SwitchStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#case-separator) */
public class CaseSeparatorRule : RulebookAstRule() {
    override fun getName(): String = "CaseSeparator"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }

    public class Visitor : RulebookVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            super.visitSwitch(node)

            // collect cases
            val statements =
                buildList<Statement> {
                    addAll(node.caseStatements)
                    node
                        .defaultStatement
                        ?.takeUnless { it.isEmpty }
                        ?.let { add(it) }
                }.takeUnless { it.isEmpty() }
                    ?: return

            // checks for violation
            val hasMultiline = statements.any { it.isMultiline() || hasCommentAbove(it) }
            for ((i, statement) in statements.withIndex()) {
                val lastBody =
                    ((statements.getOrNull(i - 1) as? CaseStatement)?.code as? BlockStatement)
                        ?.statements
                        ?.last()
                        ?: continue
                val lastStatementLastLineNumber = lastBody.lastLineNumber - 1
                val statementLineNumber = getLineNumberBefore(statement, lastBody)
                when {
                    hasMultiline ->
                        if (lastStatementLastLineNumber != statementLineNumber - 2) {
                            violations +=
                                rule.createViolation(
                                    lastStatementLastLineNumber + 1,
                                    sourceCode.line(lastStatementLastLineNumber),
                                    Messages[MSG_MISSING],
                                )
                        }

                    lastStatementLastLineNumber != statementLineNumber - 1 ->
                        violations +=
                            rule.createViolation(
                                lastStatementLastLineNumber + 1,
                                sourceCode.line(lastStatementLastLineNumber),
                                Messages[MSG_UNEXPECTED],
                            )
                }
            }
        }
    }
}
