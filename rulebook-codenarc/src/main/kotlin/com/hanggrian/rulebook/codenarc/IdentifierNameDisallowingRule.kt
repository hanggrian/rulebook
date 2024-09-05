package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#identifier-name-disallowing)
 */
public class IdentifierNameDisallowingRule : Rule() {
    internal var names =
        setOf(
            "object",
            "integer",
            "string",
            "integers",
            "strings",
        )

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "IdentifierNameDisallowing"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "identifier.name.disallowing"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitField(node: FieldNode) {
            super.visitField(node)

            // checks for violation
            val names = (rule as IdentifierNameDisallowingRule).names
            node
                .takeIf { it.name in names }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
