package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position).
 */
public class ConstructorPositionRule : RulebookRule() {
    public override fun getName(): String = "ConstructorPosition"

    public override fun getAstVisitorClass(): Class<*> = ConstructorPositionVisitor::class.java
}

public class ConstructorPositionVisitor : AbstractAstVisitor() {
    override fun visitClassComplete(node: ClassNode) {
        // avoid directly targeting constructor for efficiency
        val constructor =
            node.declaredConstructors.firstOrNull() ?: return super.visitClassComplete(node)

        // checks for violation
        if (node.fields.any { it.lineNumber > constructor.lineNumber }) {
            addViolation(constructor, Messages[MSG_PROPERTIES])
        }
        if (node.methods.any { it.lineNumber < constructor.lineNumber }) {
            addViolation(constructor, Messages[MSG_METHODS])
        }

        super.visitClassComplete(node)
    }

    internal companion object {
        const val MSG_PROPERTIES = "constructor.position.properties"
        const val MSG_METHODS = "constructor.position.methods"
    }
}
