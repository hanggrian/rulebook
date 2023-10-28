package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#invert-if-condition).
 */
class InvertIfConditionRule : RulebookRule() {
    override fun getName(): String = "InvertIfCondition"

    override fun getAstVisitorClass(): Class<*> = InvertIfConditionVisitor::class.java
}

class InvertIfConditionVisitor : AbstractAstVisitor() {
    override fun visitBlockStatement(statement: BlockStatement) {
        // only proceed on one if
        val if2 = statement.statements.singleOrNull() ?: return
        if (if2 !is IfStatement) {
            return
        }

        // skip if statement with else
        if (!if2.elseBlock.isEmpty) {
            return
        }

        // report 2 lines content
        if (';' in if2.ifBlock.text) {
            addViolation(if2, Messages[MSG])
        }
        super.visitBlockStatement(statement)
    }

    internal companion object {
        const val MSG = "invert.if.condition"
    }
}
