package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-illegal-naming)
 */
public class PropertyIllegalNamingRule : RulebookRule() {
    public var illegalProperties: String = "integer, string, list, set, map"

    override fun getName(): String = "PropertyIllegalNaming"

    override fun getAstVisitorClass(): Class<*> = PropertyIllegalNamingVisitor::class.java
}

public class PropertyIllegalNamingVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // checks for violation
        node.takeIf {
            it.name in (rule as PropertyIllegalNamingRule).illegalProperties.split(", ")
        } ?: return super.visitField(node)
        addViolation(node, Messages[MSG])

        super.visitField(node)
    }

    internal companion object {
        const val MSG = "property.illegal.naming"
    }
}
