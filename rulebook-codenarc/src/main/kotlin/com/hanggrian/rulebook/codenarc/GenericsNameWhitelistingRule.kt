package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#generics-name-whitelisting)
 */
public class GenericsNameWhitelistingRule : Rule() {
    internal var names = setOf("E", "K", "N", "T", "V")

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "GenericsNameWhitelisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "generics.name.whitelisting"

        private fun ASTNode.hasParentWithGenerics(): Boolean =
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
            val genericsType = genericTypes?.singleOrNull() ?: return

            // checks for violation
            val names = (rule as GenericsNameWhitelistingRule).names
            node
                .takeUnless { it.hasParentWithGenerics() || genericsType.name in names }
                ?: return
            addViolation(genericsType, Messages.get(MSG, names.joinToString(", ")))
        }
    }
}
