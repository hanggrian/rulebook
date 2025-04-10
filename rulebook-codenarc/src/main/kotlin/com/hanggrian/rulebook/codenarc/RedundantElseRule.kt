package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasJumpStatement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#redundant-else) */
public class RedundantElseRule : RulebookAstRule() {
    override fun getName(): String = "RedundantElse"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "redundant.else"
    }

    // do not use `visitIfElse` because it also tracks `if` and `else if` simultaneously
    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            super.visitBlockStatement(node)

            for (statement in node.statements) {
                // skip single if
                var `if`: IfStatement? =
                    (statement as? IfStatement)
                        ?.takeUnless { it.elseBlock.isEmpty }
                        ?: continue

                // checks for violation
                while (`if` != null) {
                    if (!`if`.ifBlock.hasJumpStatement()) {
                        return
                    }
                    val lastElse = `if`.elseBlock
                    if (lastElse != null) {
                        addViolation(lastElse, Messages[MSG])
                    }
                    `if` = lastElse as? IfStatement
                }
            }
        }
    }
}
