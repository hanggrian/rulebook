package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#invert-if-condition).
 */
public class InvertIfConditionRule : RulebookRule() {
    public override fun getName(): String = "InvertIfCondition"

    public override fun getAstVisitorClass(): Class<*> = InvertIfConditionVisitor::class.java
}

public class InvertIfConditionVisitor : AbstractAstVisitor() {
    public override fun visitBlockStatement(statement: BlockStatement) {
        // only proceed on one if and no else
        val if2 =
            statement.statements.singleOrNull()
                ?.takeUnless { it !is IfStatement }
                ?.takeUnless { !(it as IfStatement).elseBlock.isEmpty } as IfStatement?
                ?: return

        // report 2 lines content
        if2.ifBlock.text.takeIf { ';' in it } ?: return
        addViolation(if2, Messages[MSG])
        super.visitBlockStatement(statement)
    }

    internal companion object {
        const val MSG = "invert.if.condition"
    }
}
