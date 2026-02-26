package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.LonelyIfRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#lonely-if) */
public class LonelyIfRule : RulebookAstRule() {
    override fun getName(): String = "LonelyIf"

    override fun getAstVisitorClass(): Class<*> = LonelyIfVisitor::class.java

    internal companion object {
        const val MSG = "lonely.if"
    }
}

public class LonelyIfVisitor : RulebookVisitor() {
    override fun visitIfElse(node: IfStatement) {
        if (!isFirstVisit(node)) {
            return
        }

        // checks for violation
        val `if` =
            (node.lastElseBlock as? BlockStatement)
                ?.statements
                ?.singleOrNull()
                ?.takeIf { it is IfStatement }
                ?: return
        addViolation(`if`, Messages[MSG])
    }

    private val IfStatement.lastElseBlock: Statement?
        get() {
            (elseBlock as? IfStatement?)
                ?.let { return it.lastElseBlock }
            return elseBlock
        }
}
