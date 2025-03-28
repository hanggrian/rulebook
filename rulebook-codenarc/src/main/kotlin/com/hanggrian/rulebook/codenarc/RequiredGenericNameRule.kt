package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#required-generic-name) */
public class RequiredGenericNameRule : RulebookAstRule() {
    internal var names = setOf("E", "K", "N", "T", "V")

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "RequiredGenericName"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "required.generic.name"

        private fun ASTNode.hasParentWithGenerics() =
            when (this) {
                is MethodNode -> declaringClass.outerClasses + declaringClass
                else -> (this as ClassNode).outerClasses
            }.any { it.name != "None" && !it.genericsTypes.isNullOrEmpty() }
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            process(node, node.genericsTypes)
        }

        override fun visitMethodEx(node: MethodNode) {
            super.visitMethodEx(node)

            process(node, node.genericsTypes)
        }

        private fun process(node: ASTNode, genericTypes: Array<GenericsType>?) {
            // filter out multiple generics
            val genericsType =
                genericTypes
                    ?.singleOrNull()
                    ?: return

            // checks for violation
            val names = (rule as RequiredGenericNameRule).names
            node
                .takeUnless { it.hasParentWithGenerics() || genericsType.name in names }
                ?: return
            addViolation(genericsType, Messages.get(MSG, names.joinToString(", ")))
        }
    }
}
