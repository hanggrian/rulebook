package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.rules.RedundantElseRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.IfStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-else) */
public class RedundantElseRule : RulebookAstRule() {
    override fun getName(): String = "RedundantElse"

    override fun getAstVisitorClass(): Class<*> = RedundantElseVisitor::class.java

    internal companion object {
        const val MSG = "redundant.else"
    }
}

public class RedundantElseVisitor : RulebookVisitor() {
    private val visited = hashSetOf<IfStatement>()

    override fun visitIfElse(node: IfStatement) {
        // target root if
        val elseBlock = node.elseBlock
        if (elseBlock is IfStatement) {
            visited += elseBlock
        }
        if (node !in visited) {
            process(node)
        }
        super.visitIfElse(node)
    }

    private fun process(node: IfStatement) {
        // checks for violation
        var `if`: IfStatement? = node
        while (`if` != null) {
            `if`
                .ifBlock
                .takeIf { it.hasJumpStatement() }
                ?: return
            val `else` =
                `if`
                    .elseBlock
                    ?: return
            addViolation(`else`, Messages[MSG])
            `if` = `else` as? IfStatement
        }
    }
}
