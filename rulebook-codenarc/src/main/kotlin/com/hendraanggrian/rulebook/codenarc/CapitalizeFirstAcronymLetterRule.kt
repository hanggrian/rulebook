package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#capitalize-first-acronym-letter).
 */
class CapitalizeFirstAcronymLetterRule : RulebookRule() {
    override fun getName(): String = "CapitalizeFirstAcronymLetter"

    override fun getAstVisitorClass(): Class<*> = CapitalizeFirstAcronymLetterVisitor::class.java
}

class CapitalizeFirstAcronymLetterVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        process(node, node.name)
        super.visitClassEx(node)
    }

    override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        if (node.name.isStaticPropertyName()) {
            return super.visitField(node)
        }

        process(node, node.name)
        super.visitField(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        node.parameters.forEach { process(it, it.name) }
        process(node, node.name)
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: ASTNode, name: String) {
        // check for violation
        if (REGEX.containsMatchIn(name)) {
            addViolation(node, Messages.get(MSG, name.transform()))
        }
    }

    internal companion object {
        const val MSG = "capitalize.first.acronym.letter"

        private val REGEX = Regex("[A-Z]{3,}")

        private fun String.isStaticPropertyName(): Boolean =
            all { it.isUpperCase() || it.isDigit() || it == '_' }

        private fun String.transform(): String =
            REGEX.replace(this) {
                it.value.first() +
                    when {
                        it.range.last == lastIndex -> it.value.drop(1).lowercase()
                        else -> it.value.drop(1).dropLast(1).lowercase() + it.value.last()
                    }
            }
    }
}
