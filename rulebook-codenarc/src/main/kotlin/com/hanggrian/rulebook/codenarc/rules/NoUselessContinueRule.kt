package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoUselessContinueRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.WhileStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-useless-continue) */
public class NoUselessContinueRule : RulebookAstRule() {
    override fun getName(): String = "NoUselessContinue"

    override fun getAstVisitorClass(): Class<*> = NoUselessUnnecessaryContinueVisitor::class.java

    internal companion object {
        const val MSG = "no.useless.continue"
    }
}

public class NoUselessUnnecessaryContinueVisitor : RulebookVisitor() {
    override fun visitForLoop(node: ForStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock)
        super.visitForLoop(node)
    }

    override fun visitWhileLoop(node: WhileStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock)
        super.visitWhileLoop(node)
    }

    override fun visitDoWhileLoop(node: DoWhileStatement) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node.loopBlock)
        super.visitDoWhileLoop(node)
    }

    private fun process(node: Statement) {
        // checks for violation
        val `continue` =
            (node as? BlockStatement)
                ?.statements
                ?.lastOrNull()
                ?: node
        `continue`
            .takeIf { it is ContinueStatement }
            ?: return
        addViolation(`continue`, Messages[MSG])
    }
}
