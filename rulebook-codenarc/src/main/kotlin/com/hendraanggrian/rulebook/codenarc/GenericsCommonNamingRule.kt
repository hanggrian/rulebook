package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-common-naming)
 */
public class GenericsCommonNamingRule : RulebookRule() {
    public var commonGenerics: String = "E, K, N, T, V"

    override fun getName(): String = "GenericsCommonNaming"

    override fun getAstVisitorClass(): Class<*> = GenericsCommonNamingVisitor::class.java
}

public class GenericsCommonNamingVisitor : AbstractAstVisitor() {
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
        val generics = (rule as GenericsCommonNamingRule).commonGenerics
        node.takeIf { !it.hasParentWithGenerics() && genericType.name !in generics.split(", ") }
            ?: return
        addViolation(genericType, Messages.get(MSG, generics))
    }

    internal companion object {
        const val MSG = "generics.common.naming"

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
