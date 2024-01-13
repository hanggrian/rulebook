package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-acronym-capitalization).
 */
public class SourceAcronymCapitalizationRule : RulebookRule() {
    override fun getName(): String = "SourceAcronymCapitalization"

    override fun getAstVisitorClass(): Class<*> = SourceAcronymCapitalizationVisitor::class.java
}

public class SourceAcronymCapitalizationVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        process(node.name, node)
        super.visitClassEx(node)
    }

    override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        node.takeUnless { STATIC_PROPERTY_REGEX.matches(it.name) } ?: return super.visitField(node)

        // checks for violation
        process(node.name, node)

        super.visitField(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        // checks for violation
        node.parameters.forEach { process(it.name, it) }
        process(node.name, node)

        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(name: String, ast: ASTNode) {
        val replacement =
            name.takeIf { ACRONYM_REGEX.containsMatchIn(it) }
                ?.run {
                    ACRONYM_REGEX.replace(this) {
                        it.value.first() +
                            when {
                                it.range.last == lastIndex -> it.value.drop(1).lowercase()
                                else -> it.value.drop(1).dropLast(1).lowercase() + it.value.last()
                            }
                    }
                }
                ?: return
        addViolation(ast, Messages.get(MSG, replacement))
    }

    internal companion object {
        const val MSG = "source.acronym.capitalization"

        private val ACRONYM_REGEX = Regex("[A-Z]{3,}")
        private val STATIC_PROPERTY_REGEX = Regex("^[A-Z][A-Z0-9_]*\$")
    }
}
