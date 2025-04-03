package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.isMultiline
import com.hanggrian.rulebook.codenarc.internals.sourceLineNullable
import com.hanggrian.rulebook.codenarc.internals.trimComment
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#statement-wrap) */
public class StatementWrapRule : RulebookAstRule() {
    override fun getName(): String = "StatementWrap"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "statement.wrap"

        val FOR_REGEX = Regex("for \\(.*?\\)")
        val STRING_REGEX = Regex("[\"\'].*?[\'\"]")
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitIfElse(node: IfStatement) {
            super.visitIfElse(node)
            processControlFlow(node.ifBlock as? BlockStatement ?: return)
        }

        override fun visitForLoop(node: ForStatement) {
            super.visitForLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitWhileLoop(node: WhileStatement) {
            super.visitWhileLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitDoWhileLoop(node: DoWhileStatement) {
            super.visitDoWhileLoop(node)
            processControlFlow(node.loopBlock as? BlockStatement ?: return)
        }

        override fun visitBlockStatement(node: BlockStatement) {
            super.visitBlockStatement(node)

            for (statement in node.statements) {
                // checks for violation
                sourceLineNullable(statement)
                    ?.trimComment()
                    ?.takeIf { ';' in FOR_REGEX.replace(STRING_REGEX.replace(it, ""), "") }
                    ?: continue
                addViolation(statement, Messages.get(MSG, ';'))
            }
        }

        private fun processControlFlow(blockStatement: BlockStatement) {
            if (blockStatement.lineNumber < 0 || blockStatement.isMultiline()) {
                return
            }
            sourceLineNullable(blockStatement)
                ?.takeIf { it.indexOf('{') < it.indexOf('}') }
                ?: return
            addViolation(blockStatement, Messages.get(MSG, '{'))
        }
    }
}
