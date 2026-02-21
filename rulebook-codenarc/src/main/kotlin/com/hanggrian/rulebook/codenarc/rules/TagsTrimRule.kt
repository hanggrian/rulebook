package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.rules.TagsTrimRule.Companion.MSG_FIRST
import com.hanggrian.rulebook.codenarc.rules.TagsTrimRule.Companion.MSG_LAST
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.MethodCallExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#tags-trim) */
public class TagsTrimRule : RulebookAstRule() {
    override fun getName(): String = "TagsTrim"

    override fun getAstVisitorClass(): Class<*> = TagsTrimVisitor::class.java

    internal companion object {
        const val MSG_FIRST = "tags.trim.first"
        const val MSG_LAST = "tags.trim.last"
    }
}

public class TagsTrimVisitor : RulebookVisitor() {
    override fun visitMethodEx(node: MethodNode) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.genericsTypes ?: return, node.lineNumber, node.code.lineNumber)
        super.visitMethodEx(node)
    }

    override fun visitMethodCallExpression(node: MethodCallExpression) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.genericsTypes ?: return, node.lineNumber, node.lastLineNumber)
        super.visitMethodCallExpression(node)
    }

    private fun process(parameters: Array<GenericsType>, start: Int, end: Int) {
        val (firstElement, lastElement) =
            parameters
                .takeUnless { it.isEmpty() }
                ?.run { first() to last() }
                ?.takeIf { it.first.lineNumber > -1 && it.second.lineNumber > -1 }
                ?: return
        var lineNumber = firstElement.lineNumber
        var lastLineNumber = lastElement.lastLineNumber
        while (lineNumber > start) {
            if (sourceCode.line(lineNumber - 1).isBlank()) {
                violations += rule.createViolation(lineNumber, "", Messages[MSG_FIRST])
            }
            lineNumber--
        }
        while (lastLineNumber < end) {
            if (sourceCode.line(lastLineNumber - 1).isBlank()) {
                violations += rule.createViolation(lastLineNumber, "", Messages[MSG_LAST])
            }
            lastLineNumber++
        }
    }
}
