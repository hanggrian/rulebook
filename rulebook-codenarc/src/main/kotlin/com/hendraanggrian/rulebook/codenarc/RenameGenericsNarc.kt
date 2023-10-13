package com.hendraanggrian.rulebook.codenarc

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameGenerics).
 */
class RenameGenericsNarc : RulebookNarc() {
    internal companion object {
        const val MSG = "rename.generics"

        val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")
    }

    override var title: String = "RenameGenericType"
    override fun getAstVisitorClass(): Class<*> = RenameGenericsVisitor::class.java
}

class RenameGenericsVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        process(node.genericsTypes)
        super.visitClassEx(node)
    }

    override fun visitMethodEx(node: MethodNode) {
        process(node.genericsTypes)
        super.visitMethodEx(node)
    }

    fun process(genericTypes: Array<GenericsType>?) {
        // take generics
        if (genericTypes == null) {
            return
        }

        // filter out multiple generics
        val genericType = genericTypes.singleOrNull() ?: return

        // check for a match
        if (genericType.name !in RenameGenericsNarc.COMMON_GENERICS) {
            addViolation(genericType, Messages[RenameGenericsNarc.MSG])
        }
    }
}
