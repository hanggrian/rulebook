package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#source-acronym-capitalization)
 */
public class SourceAcronymCapitalizationRule : RulebookRule() {
    public override fun getName(): String = "SourceAcronymCapitalization"

    public override fun getAstVisitorClass(): Class<*> =
        SourceAcronymCapitalizationVisitor::class.java
}

public class SourceAcronymCapitalizationVisitor : AbstractAstVisitor() {
    public override fun visitClassEx(node: ClassNode) {
        process(node, node.name)
        super.visitClassEx(node)
    }

    public override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        node.takeUnless { it.name.isStaticPropertyName() } ?: return super.visitField(node)

        // checks for violation
        process(node, node.name)

        super.visitField(node)
    }

    public override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        // checks for violation
        node.parameters.forEach { process(it, it.name) }
        process(node, node.name)
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: ASTNode, name: String) {
        name.takeIf { REGEX.containsMatchIn(it) } ?: return
        addViolation(node, Messages.get(MSG, name.transform()))
    }

    internal companion object {
        const val MSG = "source.acronym.capitalization"

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
