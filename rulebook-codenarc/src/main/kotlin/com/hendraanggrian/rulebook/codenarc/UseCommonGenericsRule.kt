package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#use-common-generics).
 */
public class UseCommonGenericsRule : RulebookRule() {
    public var generics: String = "E, K, N, T, V"

    public override fun getName(): String = "UseCommonGenerics"

    public override fun getAstVisitorClass(): Class<*> = UseCommonGenericsVisitor::class.java
}

public class UseCommonGenericsVisitor : AbstractAstVisitor() {
    public override fun visitClassEx(node: ClassNode) {
        process(node, node.genericsTypes)
        super.visitClassEx(node)
    }

    public override fun visitMethodEx(node: MethodNode) {
        process(node, node.genericsTypes)
        super.visitMethodEx(node)
    }

    private fun process(node: ASTNode, genericTypes: Array<GenericsType>?) {
        // filter out multiple generics
        val genericType = genericTypes?.singleOrNull() ?: return

        // checks for violation
        if (!node.hasParentWithGenerics() &&
            genericType.name !in (rule as UseCommonGenericsRule).generics.split(", ")
        ) {
            addViolation(
                genericType,
                Messages.get(MSG, (rule as UseCommonGenericsRule).generics),
            )
        }
    }

    internal companion object {
        const val MSG = "use.common.generics"

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
