package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor
import java.lang.reflect.Modifier

/** [See detail](https://hanggrian.github.io/rulebook/rules/#utility-class-definition) */
public class UtilityClassDefinitionRule : RulebookAstRule() {
    override fun getName(): String = "UtilityClassDefinition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_CONSTRUCTOR = "utility.class.definition.constructor"
        const val MSG_CONSTRUCTOR_MODIFIER = "utility.class.definition.constructor.modifier"
        const val MSG_MODIFIER = "utility.class.definition.modifier"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // skip empty class, inheritance or containing non-static members
            node
                .takeIf { n ->
                    (n.methods.isNotEmpty() || n.fields.isNotEmpty()) &&
                        n.interfaces.isEmpty() &&
                        n.superClass.name == "java.lang.Object" &&
                        n.methods.all { it.isStatic } &&
                        n.fields.all { it.isStatic }
                } ?: return

            // checks for violation
            if (!Modifier.isFinal(node.modifiers)) {
                addViolation(node, Messages[MSG_MODIFIER])
            }
            if (node.declaredConstructors.isEmpty()) {
                addViolation(node, Messages[MSG_CONSTRUCTOR])
                return
            }
            node
                .declaredConstructors
                .filterNot { it.isPrivate }
                .forEach { addViolation(it, Messages[MSG_CONSTRUCTOR_MODIFIER]) }
        }
    }
}
