package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-name-acronym-capitalization)
 */
public class ClassNameAcronymCapitalizationRule : RulebookRule() {
    override fun getName(): String = "ClassNameAcronymCapitalization"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "class.name.acronym.capitalization"

        private val ABBREVIATION_REGEX = Regex("[A-Z]{3,}")
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            process(node, node.name)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            super.visitConstructorOrMethod(node, isConstructor)

            // checks for violation
            node.parameters.forEach { process(it, it.name) }
            process(node, node.name)
        }

        private fun process(node: ASTNode, name: String) {
            val transformation =
                name
                    .takeIf { ABBREVIATION_REGEX.containsMatchIn(it) }
                    ?.let { s ->
                        ABBREVIATION_REGEX.replace(s) {
                            it.value.first() +
                                when {
                                    it.range.last == s.lastIndex -> it.value.drop(1).lowercase()
                                    else ->
                                        it.value.drop(1).dropLast(1).lowercase() +
                                            it.value.last()
                                }
                        }
                    } ?: return
            addViolation(node, Messages.get(MSG, transformation))
        }
    }
}
