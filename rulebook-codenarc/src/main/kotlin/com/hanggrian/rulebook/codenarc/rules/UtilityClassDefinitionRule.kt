package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.UtilityClassDefinitionRule.Companion.MSG_CONSTRUCTOR
import com.hanggrian.rulebook.codenarc.rules.UtilityClassDefinitionRule.Companion.MSG_CONSTRUCTOR_MODIFIER
import com.hanggrian.rulebook.codenarc.rules.UtilityClassDefinitionRule.Companion.MSG_MODIFIER
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassNode
import java.lang.reflect.Modifier

/** [See detail](https://hanggrian.github.io/rulebook/rules/#utility-class-definition) */
public class UtilityClassDefinitionRule : RulebookAstRule() {
    override fun getName(): String = "UtilityClassDefinition"

    override fun getAstVisitorClass(): Class<*> = UtilityClassDefinitionVisitor::class.java

    internal companion object {
        const val MSG_CONSTRUCTOR = "utility.class.definition.constructor"
        const val MSG_CONSTRUCTOR_MODIFIER = "utility.class.definition.constructor.modifier"
        const val MSG_MODIFIER = "utility.class.definition.modifier"
    }
}

public class UtilityClassDefinitionVisitor : RulebookVisitor() {
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
