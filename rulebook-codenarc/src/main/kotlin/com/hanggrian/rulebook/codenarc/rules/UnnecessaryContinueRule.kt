package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.UnnecessaryContinueRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ContinueStatement
import org.codehaus.groovy.ast.stmt.DoWhileStatement
import org.codehaus.groovy.ast.stmt.ForStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.WhileStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-continue) */
public class UnnecessaryContinueRule : RulebookAstRule() {
    override fun getName(): String = "UnnecessaryContinue"

    override fun getAstVisitorClass(): Class<*> = UnnecessaryContinueVisitor::class.java

    internal companion object {
        const val MSG = "unnecessary.continue"
    }
}

public class UnnecessaryContinueVisitor : RulebookVisitor() {
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
