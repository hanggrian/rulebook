package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.RenameAbbreviationInIdentifierNarc.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.RenameAbbreviationInIdentifierNarc.Companion.REGEX
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameAbbreviationInIdentifier).
 */
class RenameAbbreviationInIdentifierNarc : RulebookNarc() {
    internal companion object {
        const val MSG = "rename.abbreviation.in.identifier"

        val REGEX = Regex("[A-Z]{3,}")
    }

    override var title: String = "RenameAbbreviationInIdentifier"
    override fun getAstVisitorClass(): Class<*> = RenameAbbreviationInIdentifierVisitor::class.java
}

// TODO: `visitClassEx` is not triggering
class RenameAbbreviationInIdentifierVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        if (node.name.isStaticPropertyName()) {
            return super.visitField(node)
        }

        // check for violation
        if (REGEX.containsMatchIn(node.name)) {
            addViolation(node, Messages.get(MSG, node.name.transform()))
        }
        super.visitField(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        // checks parameters first
        node.parameters.forEach {
            if (REGEX.containsMatchIn(it.name)) {
                addViolation(it, Messages.get(MSG, it.name.transform()))
            }
        }

        // check for violation
        if (REGEX.containsMatchIn(node.name)) {
            addViolation(node, Messages.get(MSG, node.name.transform()))
        }
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun String.transform(): String = REGEX.replace(this) {
        it.value.first() + when {
            it.range.last == lastIndex -> it.value.drop(1).lowercase()
            else -> it.value.drop(1).dropLast(1).lowercase() + it.value.last()
        }
    }
}
