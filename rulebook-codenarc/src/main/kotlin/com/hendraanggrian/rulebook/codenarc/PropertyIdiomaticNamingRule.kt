package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.FieldNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming).
 */
public class PropertyIdiomaticNamingRule : RulebookRule() {
    public override fun getName(): String = "PropertyIdiomaticNaming"

    public override fun getAstVisitorClass(): Class<*> = PropertyIdiomaticNamingVisitor::class.java
}

public class PropertyIdiomaticNamingVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // skip no declaration
        val typeName =
            node.type?.name
                ?.let {
                    val s = it.substringBefore('<')
                    when {
                        !it.startsWith("groovy.") && !it.startsWith("java.") -> s
                        else -> s.substringAfterLast('.')
                    }
                } ?: return super.visitField(node)

        // checks for violation
        node.takeIf {
            typeName.equals(it.name, true) &&
                (typeName in OBJECT_TYPES || typeName in COLLECTION_TYPES)
        } ?: return super.visitField(node)
        addViolation(node, Messages[MSG])

        super.visitField(node)
    }

    internal companion object {
        const val MSG = "property.idiomatic.naming"

        private val OBJECT_TYPES =
            setOf(
                "Boolean",
                "boolean",
                "Byte",
                "byte",
                "Char",
                "char",
                "Double",
                "double",
                "Float",
                "float",
                "Int",
                "int",
                "Long",
                "long",
                "Short",
                "short",
                "String",
                "string",
            )
        private val COLLECTION_TYPES =
            setOf(
                "Set",
                "List",
                "Map",
                "Iterable",
                "Collection",
            )
    }
}
