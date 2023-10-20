package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import com.hendraanggrian.rulebook.codenarc.internals.RulebookNarc
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameUncommonGenerics).
 */
class RenameUncommonGenericsNarc : RulebookNarc() {
    override fun getName(): String = "RenameUncommonGenerics"

    override fun getAstVisitorClass(): Class<*> = RenameUncommonGenericsVisitor::class.java
}

class RenameUncommonGenericsVisitor : AbstractAstVisitor() {
    override fun visitClassEx(node: ClassNode) {
        process(node.genericsTypes)
        super.visitClassEx(node)
    }

    override fun visitMethodEx(node: MethodNode) {
        process(node.genericsTypes)
        super.visitMethodEx(node)
    }

    private fun process(genericTypes: Array<GenericsType>?) {
        // take generics
        if (genericTypes == null) {
            return
        }

        // filter out multiple generics
        val genericType = genericTypes.singleOrNull() ?: return

        // check for a match
        if (genericType.name !in COMMON_GENERICS) {
            addViolation(genericType, Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "rename.uncommon.generics"

        private val COMMON_GENERICS = setOf("E", "K", "N", "T", "V")
    }
}
