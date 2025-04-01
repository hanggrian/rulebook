package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-variable-name) */
public class IllegalVariableNameRule : RulebookAstRule() {
    internal var names =
        setOf(
            "object",
            "integer",
            "string",
            "objects",
            "integers",
            "strings",
        )

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "IllegalVariableName"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "illegal.variable.name"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitField(node: FieldNode) {
            super.visitField(node)

            // checks for violation
            val names = (rule as IllegalVariableNameRule).names
            node
                .takeIf { it.name in names }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
