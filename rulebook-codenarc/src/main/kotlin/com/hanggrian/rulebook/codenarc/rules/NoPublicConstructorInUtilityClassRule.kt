package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.NoPublicConstructorInUtilityClassRule.Companion.MSG_CONSTRUCTOR
import com.hanggrian.rulebook.codenarc.rules.NoPublicConstructorInUtilityClassRule.Companion.MSG_MODIFIER
import org.codehaus.groovy.ast.ClassNode

/**
 * [See detail](https://hanggrian.github.io/rulebook/rules/#no-public-constructor-in-utility-class)
 */
public class NoPublicConstructorInUtilityClassRule : RulebookAstRule() {
    override fun getName(): String = "NoPublicConstructorInUtilityClass"

    override fun getAstVisitorClass(): Class<*> =
        NoPublicConstructorInUtilityClassVisitor::class.java

    internal companion object {
        const val MSG_CONSTRUCTOR = "no.public.constructor.in.utility.class.constructor"
        const val MSG_MODIFIER = "no.public.constructor.in.utility.class.modifier"
    }
}

public class NoPublicConstructorInUtilityClassVisitor : RulebookVisitor() {
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
            .takeIf { node.declaredConstructors.isEmpty() }
            ?.let {
                addViolation(node, Messages[MSG_CONSTRUCTOR])
                return super.visitClassEx(node)
            }
        node
            .declaredConstructors
            .filterNot { it.isPrivate }
            .forEach { addViolation(it, Messages[MSG_MODIFIER]) }

        super.visitClassEx(node)
    }
}
