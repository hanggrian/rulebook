package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoLonelyIfRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-lonely-if) */
public class NoLonelyIfRule : RulebookAstRule() {
    override fun getName(): String = "NoLonelyIf"

    override fun getAstVisitorClass(): Class<*> = NoLonelyIfVisitor::class.java

    internal companion object {
        const val MSG = "no.lonely.if"
    }
}

public class NoLonelyIfVisitor : RulebookVisitor() {
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
                ?: return super.visitIfElse(node)
        addViolation(`if`, Messages[MSG])

        super.visitIfElse(node)
    }

    private val IfStatement.lastElseBlock: Statement?
        get() {
            (elseBlock as? IfStatement?)
                ?.let { return it.lastElseBlock }
            return elseBlock
        }
}
