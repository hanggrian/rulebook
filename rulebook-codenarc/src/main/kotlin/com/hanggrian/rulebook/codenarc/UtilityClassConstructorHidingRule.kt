package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#utility-class-constructor-hiding)
 */
public class UtilityClassConstructorHidingRule : Rule() {
    override fun getName(): String = "UtilityClassConstructorHiding"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_NEW = "utility.class.constructor.hiding.new"
        const val MSG_EXIST = "utility.class.constructor.hiding.exist"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            // skip empty class
            if (node.methods.isEmpty() && node.fields.isEmpty()) {
                return super.visitClassEx(node)
            }

            // skip if class has non-static member
            if (node.methods.any { !it.isStatic } || node.fields.any { !it.isStatic }) {
                return super.visitClassEx(node)
            }

            // checks for violation
            if (node.declaredConstructors.isEmpty()) {
                addViolation(node, Messages[MSG_NEW])
                return super.visitClassEx(node)
            }
            for (constructor in node.declaredConstructors) {
                if (!constructor.isPrivate) {
                    addViolation(constructor, Messages[MSG_EXIST])
                }
            }

            super.visitClassEx(node)
        }
    }
}
