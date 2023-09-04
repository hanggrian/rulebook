package com.hendraanggrian.rulebook.codenarc

import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/InvertIfCondition).
 */
class InvertIfConditionNarc : RulebookNarc() {
    internal companion object {
        const val MSG = "invert.if.condition"
    }

    override var title: String = "InvertIfCondition"
    override fun getAstVisitorClass(): Class<*> = InvertIfConditionVisitor::class.java
}

class InvertIfConditionVisitor : AbstractAstVisitor() {
    override fun visitBlockStatement(statement: BlockStatement) {
        // only proceed on one if
        val `if` = statement.statements.singleOrNull() ?: return
        if (`if` !is IfStatement) {
            return
        }

        // skip if statement with else
        if (!`if`.elseBlock.isEmpty) {
            return
        }

        // report 2 lines content
        if (';' in `if`.ifBlock.text) {
            addViolation(`if`, Messages[InvertIfConditionNarc.MSG])
        }
        super.visitBlockStatement(statement)
    }
}
