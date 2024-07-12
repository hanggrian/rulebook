package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#identifier-name-blacklisting)
 */
public class IdentifierNameBlacklistingRule : Rule() {
    private var names = "integer, string, list, set, map"

    public fun setNames(names: String) {
        this.names = names
    }

    override fun getName(): String = "IdentifierNameBlacklisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "identifier.name.blacklisting"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitField(node: FieldNode) {
            // checks for violation
            node
                .takeIf {
                    it.name in (rule as IdentifierNameBlacklistingRule).names.split(", ")
                } ?: return super.visitField(node)
            addViolation(node, Messages[MSG])

            super.visitField(node)
        }
    }
}
