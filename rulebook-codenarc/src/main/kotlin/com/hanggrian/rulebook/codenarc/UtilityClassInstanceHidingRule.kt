package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor
import java.lang.reflect.Modifier

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#utility-class-instance-hiding)
 */
public class UtilityClassInstanceHidingRule : RulebookRule() {
    override fun getName(): String = "UtilityClassInstanceHiding"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_CONSTRUCTOR = "utility.class.instance.hiding.constructor"
        const val MSG_CONSTRUCTOR_MODIFIER = "utility.class.instance.hiding.constructor.modifier"
        const val MSG_MODIFIER = "utility.class.instance.hiding.modifier"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // skip empty class or contains all non-static members
            node
                .takeUnless { it.methods.isEmpty() && node.fields.isEmpty() }
                ?.takeUnless { n ->
                    n.methods.any { !it.isStatic } ||
                        n.fields.any { !it.isStatic }
                } ?: return

            // checks for violation
            if (!Modifier.isFinal(node.modifiers)) {
                addViolation(node, Messages[MSG_MODIFIER])
            }
            if (node.declaredConstructors.isEmpty()) {
                addViolation(node, Messages[MSG_CONSTRUCTOR])
                return
            }
            for (constructor in node.declaredConstructors) {
                if (!constructor.isPrivate) {
                    addViolation(constructor, Messages[MSG_CONSTRUCTOR_MODIFIER])
                }
            }
        }
    }
}
