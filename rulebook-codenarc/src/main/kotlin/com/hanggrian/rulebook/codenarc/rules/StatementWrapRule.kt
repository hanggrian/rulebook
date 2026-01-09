package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.isMultiline
import com.hanggrian.rulebook.codenarc.rules.StatementWrapRule.Companion.FOR_REGEX
import com.hanggrian.rulebook.codenarc.rules.StatementWrapRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.StatementWrapRule.Companion.STRING_REGEX
import com.hanggrian.rulebook.codenarc.sourceLineNullable
import com.hanggrian.rulebook.codenarc.trimComment
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.WhileStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#statement-wrap) */
public class StatementWrapRule : RulebookAstRule() {
    override fun getName(): String = "StatementWrap"

    override fun getAstVisitorClass(): Class<*> = StatementWrapVisitor::class.java

    internal companion object {
        const val MSG = "statement.wrap"

        val FOR_REGEX = Regex("""for\s\(.*?\)""".trimMargin())
        val STRING_REGEX = Regex("""["'].*?['"]""")
    }
}

public class StatementWrapVisitor : RulebookVisitor() {
    override fun visitIfElse(node: IfStatement) {
        super.visitIfElse(node)
        processControlFlow(node.ifBlock as? BlockStatement)
    }

    override fun visitForLoop(node: ForStatement) {
        super.visitForLoop(node)
        processControlFlow(node.loopBlock as? BlockStatement)
    }

    override fun visitWhileLoop(node: WhileStatement) {
        super.visitWhileLoop(node)
        processControlFlow(node.loopBlock as? BlockStatement)
    }

    override fun visitDoWhileLoop(node: DoWhileStatement) {
        super.visitDoWhileLoop(node)
        processControlFlow(node.loopBlock as? BlockStatement)
    }

    private fun processControlFlow(blockStatement: BlockStatement?) {
        // checks for violation
        blockStatement
            ?.takeUnless { it.isMultiline() }
            ?.let { sourceLineNullable(it) }
            ?.trimComment()
            ?.takeIf {
                val lcurlyIndex = it.lastIndexOf('{')
                val rcurlyIndex = it.lastIndexOf('}')
                lcurlyIndex < rcurlyIndex &&
                    it.substring(lcurlyIndex + 1, rcurlyIndex).isNotBlank()
            } ?: return
        addViolation(blockStatement, Messages[MSG, '{'])
    }

    override fun visitBlockStatement(node: BlockStatement) {
        super.visitBlockStatement(node)

        for (statement in node.statements) {
            // checks for violation
            sourceLineNullable(statement)
                ?.trimComment()
                ?.takeIf {
                    val line = FOR_REGEX.replace(STRING_REGEX.replace(it, ""), "")
                    ';' in line && line.substringAfterLast(';').isNotBlank()
                } ?: continue
            addViolation(statement, Messages[MSG, ';'])
        }
    }
}
