package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.getLineNumberBefore
import com.hanggrian.rulebook.codenarc.internals.hasCommentAbove
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.CaseStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.Violation

/** [See detail](https://hanggrian.github.io/rulebook/rules/#case-separator) */
public class CaseSeparatorRule : RulebookAstRule() {
    override fun getName(): String = "CaseSeparator"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_MISSING = "case.separator.missing"
        const val MSG_UNEXPECTED = "case.separator.unexpected"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitSwitch(node: SwitchStatement) {
            super.visitSwitch(node)

            // collect cases
            val statements =
                buildList<Statement> {
                    addAll(node.caseStatements)
                    if (!node.defaultStatement.isEmpty) {
                        add(node.defaultStatement)
                    }
                }.takeUnless { it.isEmpty() }
                    ?: return

            for ((i, statement) in statements.withIndex()) {
                // targeting switch, skip first branch
                val lastStatement = statements.getOrNull(i - 1) ?: continue
                val lastBody =
                    ((lastStatement as CaseStatement).code as BlockStatement)
                        .statements
                        .last()
                val lastStatementLastLineNumber = lastBody.lastLineNumber - 1

                // checks for violation
                val statementLineNumber = getLineNumberBefore(statement, lastBody)
                when {
                    lastStatement.isMultiline() || hasCommentAbove(lastStatement) ->
                        if (lastStatementLastLineNumber != statementLineNumber - 2) {
                            violations +=
                                Violation().apply {
                                    rule = this@Visitor.rule
                                    lineNumber = lastStatementLastLineNumber + 1
                                    sourceLine = sourceCode.line(lastStatementLastLineNumber)
                                    message = Messages[MSG_MISSING]
                                }
                        }

                    lastStatementLastLineNumber != statementLineNumber - 1 ->
                        violations +=
                            Violation().apply {
                                rule = this@Visitor.rule
                                lineNumber = lastStatementLastLineNumber + 1
                                sourceLine = sourceCode.line(lastStatementLastLineNumber)
                                message = Messages[MSG_UNEXPECTED]
                            }
                }
            }
        }
    }
}
