package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.MissingPrivateConstructorRule.Companion.MSG_CONSTRUCTOR
import com.hanggrian.rulebook.codenarc.rules.MissingPrivateConstructorRule.Companion.MSG_CONSTRUCTOR_MODIFIER
import com.hanggrian.rulebook.codenarc.rules.MissingPrivateConstructorRule.Companion.MSG_MODIFIER
import org.codehaus.groovy.ast.ClassNode
import java.lang.reflect.Modifier

/** [See detail](https://hanggrian.github.io/rulebook/rules/#missing-private-constructor) */
public class MissingPrivateConstructorRule : RulebookAstRule() {
    override fun getName(): String = "MissingPrivateConstructor"

    override fun getAstVisitorClass(): Class<*> = MissingPrivateConstructorVisitor::class.java

    internal companion object {
        const val MSG_CONSTRUCTOR = "missing.private.constructor.constructor"
        const val MSG_CONSTRUCTOR_MODIFIER = "missing.private.constructor.constructor.modifier"
        const val MSG_MODIFIER = "missing.private.constructor.modifier"
    }
}

public class MissingPrivateConstructorVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        super.visitClassEx(node)

        // skip empty class, inheritance or containing non-static members
        val methods =
            node
                .takeIf { n ->
                    (n.methods.isNotEmpty() || n.fields.isNotEmpty()) &&
                        n.interfaces.isEmpty() &&
                        n.superClass.name == "java.lang.Object" &&
                        n.methods.all { it.isStatic } &&
                        n.fields.all { it.isStatic }
                }?.methods
                ?: return

        // skip class with main function
        methods
            .takeUnless { m -> m.any { it.name == "main" } }
            ?: return

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
