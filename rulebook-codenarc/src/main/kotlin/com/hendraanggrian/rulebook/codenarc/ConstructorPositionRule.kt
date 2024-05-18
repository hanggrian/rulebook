package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position)
 */
public class ConstructorPositionRule : RulebookRule() {
    override fun getName(): String = "ConstructorPosition"

    override fun getAstVisitorClass(): Class<*> = ConstructorPositionVisitor::class.java
}

public class ConstructorPositionVisitor : AbstractAstVisitor() {
    override fun visitClassComplete(node: ClassNode) {
        // there may be multiple constructors in JVM, target class instead for efficiency
        val constructor =
            node.declaredConstructors.firstOrNull() ?: return super.visitClassComplete(node)

        // checks for violation
        node.fields.filter { it.lineNumber > constructor.lineNumber }
            .forEach { addViolation(it, Messages[MSG_PROPERTIES]) }
        node.methods.filter { it.lineNumber < constructor.lineNumber }
            .forEach { addViolation(it, Messages[MSG_METHODS]) }

        super.visitClassComplete(node)
    }

    internal companion object {
        const val MSG_PROPERTIES = "constructor.position.properties"
        const val MSG_METHODS = "constructor.position.methods"
    }
}
