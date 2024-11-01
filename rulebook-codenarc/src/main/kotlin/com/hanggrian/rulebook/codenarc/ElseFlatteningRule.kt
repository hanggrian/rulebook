package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasJumpStatement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codenarc.rule.AbstractAstVisitor

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#else-flattening) */
public class ElseFlatteningRule : RulebookRule() {
    override fun getName(): String = "ElseFlattening"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "else.flattening"
    }

    // Do not use `visitIfElse` because it also tracks `if` and `else if` simultaneously.
    public class Visitor : AbstractAstVisitor() {
        override fun visitBlockStatement(node: BlockStatement) {
            super.visitBlockStatement(node)

            for (statement in node.statements) {
                // skip single if
                val `if` =
                    (statement as? IfStatement)
                        ?.takeUnless { it.elseBlock.isEmpty }
                        ?: continue

                // checks for violation
                var lastElse: Statement? = null
                var currentIf: IfStatement? = `if`
                while (currentIf != null) {
                    currentIf
                        .takeIf { it.hasJumpStatement() }
                        ?: return
                    lastElse = currentIf.elseBlock
                    currentIf = lastElse as? IfStatement
                }
                lastElse ?: return
                addViolation(lastElse, Messages[MSG])
            }
        }
    }
}
