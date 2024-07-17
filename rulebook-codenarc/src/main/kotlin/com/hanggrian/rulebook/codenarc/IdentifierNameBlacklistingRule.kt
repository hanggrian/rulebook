package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#identifier-name-blacklisting)
 */
public class IdentifierNameBlacklistingRule : Rule() {
    internal var names = setOf("integer", "string", "list", "set", "map")

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "IdentifierNameBlacklisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "identifier.name.blacklisting"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitField(node: FieldNode) {
            super.visitField(node)

            // checks for violation
            val names = (rule as IdentifierNameBlacklistingRule).names
            node
                .takeIf { it.name in names }
                ?: return
            addViolation(node, Messages[MSG])
        }
    }
}
