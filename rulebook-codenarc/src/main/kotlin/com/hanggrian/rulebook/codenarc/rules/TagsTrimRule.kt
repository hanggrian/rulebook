package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.createViolation
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.MethodCallExpression

/** [See detail](https://hanggrian.github.io/rulebook/rules/#tags-trim) */
public class TagsTrimRule : RulebookAstRule() {
    override fun getName(): String = "TagsTrim"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_FIRST = "tags.trim.first"
        const val MSG_LAST = "tags.trim.last"
    }

    public class Visitor : RulebookVisitor() {
        override fun visitMethodEx(node: MethodNode) {
            super.visitMethodEx(node)
            process(node.genericsTypes ?: return, node.lineNumber, node.code.lineNumber)
        }

        override fun visitMethodCallExpression(node: MethodCallExpression) {
            super.visitMethodCallExpression(node)
            process(node.genericsTypes ?: return, node.lineNumber, node.lastLineNumber)
        }

        private fun process(parameters: Array<GenericsType>, start: Int, end: Int) {
            val (firstElement, lastElement) =
                parameters
                    .takeUnless { it.isEmpty() }
                    ?.let { it.first() to it.last() }
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
}
