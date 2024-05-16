package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming)
 */
public class PropertyIdiomaticNamingRule : RulebookRule() {
    public var prohibitedProperties: String = "integer, string, list, set, map"

    override fun getName(): String = "PropertyIdiomaticNaming"

    override fun getAstVisitorClass(): Class<*> = PropertyIdiomaticNamingVisitor::class.java
}

public class PropertyIdiomaticNamingVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // checks for violation
        node.takeIf {
            it.name in (rule as PropertyIdiomaticNamingRule).prohibitedProperties.split(", ")
        } ?: return super.visitField(node)
        addViolation(node, Messages[MSG])

        super.visitField(node)
    }

    internal companion object {
        const val MSG = "property.idiomatic.naming"
    }
}
