package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-name-whitelisting)
 */
public class GenericsNameWhitelistingRule : Rule() {
    private var names = "E, K, N, T, V"

    public fun setNames(names: String) {
        this.names = names
    }

    override fun getName(): String = "GenericsNameWhitelisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

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
            val genericType = genericTypes?.singleOrNull() ?: return

            // checks for violation
            val generics = (rule as GenericsNameWhitelistingRule).names
            node
                .takeUnless {
                    it.hasParentWithGenerics() || genericType.name in generics.split(", ")
                }
                ?: return
            addViolation(genericType, Messages.get(MSG, generics))
        }
    }

    internal companion object {
        const val MSG = "generics.name.whitelisting"

        private fun ASTNode.hasParentWithGenerics(): Boolean {
            val parents =
                when (this) {
                    is MethodNode ->
                        declaringClass.outerClasses.toMutableList().also { it.add(declaringClass) }
                    else -> (this as ClassNode).outerClasses
                }
            return parents.any { it.name != "None" && !it.genericsTypes.isNullOrEmpty() }
        }
    }
}
