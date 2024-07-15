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
    private var names = "E, K, N, T, V"

    public fun setNames(names: String) {
        this.names = names
    }

    override fun getName(): String = "GenericsNameWhitelisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "generics.name.whitelisting"

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            val parents =
                when (this) {
                    is MethodNode ->
                        declaringClass.outerClasses + declaringClass
                    else -> (this as ClassNode).outerClasses
                }
            return parents.any { it.name != "None" && !it.genericsTypes.isNullOrEmpty() }
        }
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            process(node, node.genericsTypes)
            super.visitClassEx(node)
        }

        override fun visitMethodEx(node: MethodNode) {
            process(node, node.genericsTypes)
            super.visitMethodEx(node)
        }

        private fun process(node: ASTNode, genericTypes: Array<GenericsType>?) {
            // filter out multiple generics
            val genericsType = genericTypes?.singleOrNull() ?: return

            // checks for violation
            val names = (rule as GenericsNameWhitelistingRule).names
            node
                .takeUnless {
                    it.hasParentWithGenerics() || genericsType.name in names.split(", ")
                } ?: return
            addViolation(genericsType, Messages.get(MSG, names))
        }
    }
}
