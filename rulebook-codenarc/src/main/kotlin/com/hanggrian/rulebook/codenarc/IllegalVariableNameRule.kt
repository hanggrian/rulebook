package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-variable-name) */
public class IllegalVariableNameRule : RulebookAstRule() {
    public var names: String = "object, integer, string, objects, integers, strings"

    internal val nameList get() = names.split(',').map { it.trim() }

    override fun getName(): String = "IllegalVariableName"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG = "illegal.variable.name"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitField(node: FieldNode) {
            super.visitField(node)

            // checks for violation
            val names = (rule as IllegalVariableNameRule).nameList
            node
                .name
                .takeIf { it in names }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
