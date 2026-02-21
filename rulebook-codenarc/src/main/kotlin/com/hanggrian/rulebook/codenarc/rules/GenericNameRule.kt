package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.GenericNameRule.Companion.hasParentWithGenerics
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#generic-name) */
public class GenericNameRule : RulebookAstRule() {
    override fun getName(): String = "GenericName"

    override fun getAstVisitorClass(): Class<*> = RequiredGenericsNameVisitor::class.java

    internal companion object {
        const val MSG = "generic.name"

        fun ASTNode.hasParentWithGenerics() =
            when (this) {
                is MethodNode -> declaringClass.outerClasses + declaringClass
                else -> (this as ClassNode).outerClasses
            }.any { it.name != "None" && !it.genericsTypes.isNullOrEmpty() }
    }
}

public class RequiredGenericsNameVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node, node.genericsTypes)
        super.visitClassEx(node)
    }

    override fun visitConstructorOrMethod(node: MethodNode, isConstructor: Boolean) {
        if (!isFirstVisit(node)) {
            return
        }
        process(node, node.genericsTypes)
        super.visitConstructorOrMethod(node, isConstructor)
    }

    private fun process(node: ASTNode, genericTypes: Array<GenericsType>?) {
        // filter out multiple generics
        val genericsType =
            genericTypes
                ?.singleOrNull()
                ?: return

        // skip inner generics
        node
            .takeUnless { it.hasParentWithGenerics() }
            ?: return

        // checks for violation
        genericsType
            .takeUnless {
                it.name.singleOrNull()?.isUpperCase() == true
            } ?: return
        addViolation(genericsType, Messages[MSG])
    }
}
