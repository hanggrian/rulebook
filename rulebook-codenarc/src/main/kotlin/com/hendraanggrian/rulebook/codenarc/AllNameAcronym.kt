package com.hendraanggrian.rulebook.codenarc

import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/AllNameAcronym).
 */
class AllNameAcronym : RulebookRule() {
    internal companion object {
        const val MSG = "all.name.acronym.others"
    }

    override var title: String = "AllNameAcronym"
    override fun getAstVisitorClass(): Class<*> = AllNameAcronymVisitor::class.java
}

// TODO: `visitClassEx` is not triggering
class AllNameAcronymVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        val fieldName = node.name
        if (fieldName.all { it.isUpperCase() || it.isDigit() || it == '_' }) {
            return
        }

        // check for violation
        if (fieldName.isViolation()) {
            addViolation(node, Messages.get(AllNameAcronym.MSG, "field", fieldName))
        }
        super.visitField(node)
    }

    override fun visitMethodEx(node: MethodNode) {
        // check for violation
        val methodName = node.name
        if (methodName.isViolation()) {
            addViolation(node, Messages.get(AllNameAcronym.MSG, "method", methodName))
        }
        super.visitMethodEx(node)
    }

    private fun String.isViolation(): Boolean {
        // find 3 connecting uppercase letters
        for (i in 0 until length - 2) {
            if (get(i).isUpperCase() && get(i + 1).isUpperCase() && get(i + 2).isUpperCase()) {
                return true
            }
        }
        return false
    }
}
