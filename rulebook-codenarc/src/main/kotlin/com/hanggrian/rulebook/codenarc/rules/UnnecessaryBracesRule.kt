package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryBracesRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-braces) */
public class UnnecessaryBracesRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessaryBraces"

    override fun getAstVisitorClass(): Class<*> = UnnecessaryBracesVisitor::class.java

    internal companion object {
        const val MSG = "unnecessary.braces"
    }
}

public class UnnecessaryBracesVisitor : RulebookVisitor() {
    override fun visitIfElse(node: IfStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        val elseBlock = node.lastElseBlock
        (elseBlock as? BlockStatement)
            ?.statements
            ?.singleOrNull()
            ?.takeIf { it is IfStatement }
            ?: return
        addViolation(elseBlock, Messages[MSG])
    }

    private val IfStatement.lastElseBlock: Statement?
        get() {
            (elseBlock as? IfStatement?)
                ?.let { return it.lastElseBlock }
            return elseBlock
        }
}
