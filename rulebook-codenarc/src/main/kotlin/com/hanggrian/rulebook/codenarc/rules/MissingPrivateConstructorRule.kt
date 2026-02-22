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
        if (!isFirstVisit(node)) {
            return
        }

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
                ?: return super.visitClassEx(node)

        // skip class with main function
        methods
            .takeUnless { m -> m.any { it.name == "main" } }
            ?: return super.visitClassEx(node)

        // checks for violation
        node
            .takeUnless { Modifier.isFinal(it.modifiers) }
            ?.let { addViolation(it, Messages[MSG_MODIFIER]) }
        node
            .takeIf { node.declaredConstructors.isEmpty() }
            ?.let {
                addViolation(node, Messages[MSG_CONSTRUCTOR])
                return super.visitClassEx(it)
            }
        node
            .declaredConstructors
            .filterNot { it.isPrivate }
            .forEach { addViolation(it, Messages[MSG_CONSTRUCTOR_MODIFIER]) }

        super.visitClassEx(node)
    }
}
