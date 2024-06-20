package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#utility-class-instance-hiding)
 */
public class UtilityClassInstanceHidingRule : Rule() {
    override fun getName(): String = "UtilityClassInstanceHiding"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_NEW = "utility.class.instance.hiding.new"
        const val MSG_EXIST = "utility.class.instance.hiding.exist"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            // skip empty class
            if (node.methods.isEmpty() && node.fields.isEmpty()) {
                return
            }

            // skip if class has non-static member
            if (node.methods.any { !it.isStatic } || node.fields.any { !it.isStatic }) {
                return
            }

            // checks for violation
            if (node.declaredConstructors.isEmpty()) {
                addViolation(node, Messages[MSG_NEW])
                return
            }
            for (constructor in node.declaredConstructors) {
                if (!constructor.isPrivate) {
                    addViolation(constructor, Messages[MSG_EXIST])
                }
            }
        }
    }
}
