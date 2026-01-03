package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.rules.BracketsTrimRule.Companion.MSG_FIRST
import com.hanggrian.rulebook.codenarc.rules.BracketsTrimRule.Companion.MSG_LAST
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ListExpression
import org.codehaus.groovy.ast.expr.MapExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#brackets-trim) */
public class BracketsTrimRule : RulebookAstRule() {
    override fun getName(): String = "BracketsTrim"

    override fun getAstVisitorClass(): Class<*> = BracketsTrimVisitor::class.java

    internal companion object {
        const val MSG_FIRST = "brackets.trim.first"
        const val MSG_LAST = "brackets.trim.last"
    }
}

public class BracketsTrimVisitor : RulebookVisitor() {
    override fun visitListExpression(node: ListExpression) {
        super.visitListExpression(node)
        process(node.expressions, node)
    }

    override fun visitMapExpression(node: MapExpression) {
        super.visitMapExpression(node)
        process(node.mapEntryExpressions, node)
    }

    private fun process(parameters: List<Expression>, node: ASTNode) {
        val (firstElement, lastElement) =
            parameters
                .takeUnless { it.isEmpty() }
                ?.let { it.first() to it.last() }
                ?.takeIf { it.first.lineNumber > -1 && it.second.lineNumber > -1 }
                ?: return
        var lineNumber = firstElement.lineNumber
        var lastLineNumber = lastElement.lastLineNumber
        while (lineNumber > node.lineNumber) {
            if (sourceCode.line(lineNumber - 1).isBlank()) {
                violations +=
                    rule.createViolation(
                        lineNumber,
                        "",
                        Messages[MSG_FIRST],
                    )
            }
            lineNumber--
        }
        while (lastLineNumber < node.lastLineNumber) {
            if (sourceCode.line(lastLineNumber - 1).isBlank()) {
                violations +=
                    rule.createViolation(
                        lastLineNumber,
                        "",
                        Messages[MSG_LAST],
                    )
            }
            lastLineNumber++
        }
    }
}
