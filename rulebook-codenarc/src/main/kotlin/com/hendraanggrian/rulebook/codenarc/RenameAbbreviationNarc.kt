package com.hendraanggrian.rulebook.codenarc

import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/RenameAbbreviation).
 */
class RenameAbbreviationNarc : RulebookNarc() {
    internal companion object {
        const val MSG = "rename.abbreviation.others"
    }

    override var title: String = "RenameAbbreviation"
    override fun getAstVisitorClass(): Class<*> = RenameAbbreviationVisitor::class.java
}

// TODO: `visitClassEx` is not triggering
class RenameAbbreviationVisitor : AbstractAstVisitor() {
    override fun visitField(node: FieldNode) {
        // allow all uppercase, which usually is static property
        val fieldName = node.name
        if (fieldName.isStaticPropertyName()) {
            return
        }

        // check for violation
        if (fieldName.isViolation()) {
            addViolation(
                node,
                Messages.get(RenameAbbreviationNarc.MSG, "Field", fieldName.transform())
            )
        }
        super.visitField(node)
    }

    override fun visitMethodEx(node: MethodNode) {
        // check for violation
        val methodName = node.name
        if (methodName.isViolation()) {
            addViolation(
                node,
                Messages.get(RenameAbbreviationNarc.MSG, "Method", methodName.transform())
            )
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

    private fun String.transform(): String {
        val reversed = reversed()
        val sb = StringBuilder()
        reversed.forEachIndexed { index, c ->
            sb.append(
                when {
                    c.isUpperCase() && reversed.getOrNull(index + 1)?.isUpperCase() == true ->
                        c.lowercase()
                    else -> c
                }
            )
        }
        return sb.reverse().toString()
    }
}
