package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-name-acronym-capitalization)
 */
public class ClassNameAcronymCapitalizationRule : Rule() {
    override fun getName(): String = "ClassNameAcronymCapitalization"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "class.name.acronym.capitalization"

        private val REGEX = Regex("[A-Z]{3,}")

        private fun String.transform(): String =
            REGEX.replace(this) {
                it.value.first() +
                    when {
                        it.range.last == lastIndex -> it.value.drop(1).lowercase()
                        else ->
                            it.value.drop(1).dropLast(1).lowercase() +
                                it.value.last()
                    }
            }
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            process(node, node.name)
            super.visitClassEx(node)
        }

        override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
            // checks for violation
            node.parameters.forEach { process(it, it.name) }
            process(node, node.name)

            super.visitConstructorOrMethod(node, isConstructor)
        }

        private fun process(node: ASTNode, name: String) {
            name.takeIf { REGEX.containsMatchIn(it) } ?: return
            addViolation(node, Messages.get(MSG, name.transform()))
        }
    }
}
