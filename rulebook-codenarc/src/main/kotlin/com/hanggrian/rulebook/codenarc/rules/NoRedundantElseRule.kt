package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasJumpStatement
import com.hanggrian.rulebook.codenarc.rules.NoRedundantElseRule.Companion.MSG
import org.codehaus.groovy.ast.stmt.IfStatement

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-redundant-else) */
public class NoRedundantElseRule : RulebookAstRule() {
    override fun getName(): String = "NoRedundantElse"

    override fun getAstVisitorClass(): Class<*> = NoRedundantElseVisitor::class.java

    internal companion object {
        const val MSG = "no.redundant.else"
    }
}

public class NoRedundantElseVisitor : RulebookVisitor() {
    private val visited = hashSetOf<IfStatement>()

    override fun visitIfElse(node: IfStatement) {
        // target root if
        val elseBlock = node.elseBlock
        if (elseBlock is IfStatement) {
            visited += elseBlock
        }
        if (node !in visited) {
            // checks for violation
            var `if`: IfStatement? = node
            while (`if` != null) {
                `if`
                    .ifBlock
                    ?.takeIf { it.hasJumpStatement() }
                    ?: return
                val `else` =
                    `if`
                        .elseBlock
                        ?: return
                addViolation(`else`, Messages[MSG])
                `if` = `else` as? IfStatement
            }
        }
        super.visitIfElse(node)
    }
}
