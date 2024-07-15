package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-organization)
 */
public class ClassOrganizationRule : Rule() {
    override fun getName(): String = "ClassOrganization"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "class.organization"
        private const val MSG_PROPERTY = "property"
        private const val MSG_CONSTRUCTOR = "constructor"
        private const val MSG_FUNCTION = "function"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            // get indices of first members
            val firstConstructorIndex =
                node.declaredConstructors
                    .minOfOrNull { it.lineNumber }
            val firstFunctionIndex =
                node.methods
                    .filter { !it.isStatic }
                    .minOfOrNull { it.lineNumber }

            for (field in node.fields) {
                // in Groovy, static members have specific keyword
                if (field.isStatic) {
                    continue
                }

                // checks for violation
                when {
                    firstConstructorIndex != null && field.lineNumber > firstConstructorIndex ->
                        addViolation(
                            field,
                            Messages.get(MSG, Messages[MSG_PROPERTY], Messages[MSG_CONSTRUCTOR]),
                        )
                    firstFunctionIndex != null && field.lineNumber > firstFunctionIndex ->
                        addViolation(
                            field,
                            Messages.get(MSG, Messages[MSG_PROPERTY], Messages[MSG_FUNCTION]),
                        )
                }
            }
            // checks for violation
            firstFunctionIndex ?: return
            node.declaredConstructors
                .filter { it.lineNumber > firstFunctionIndex }
                .forEach {
                    addViolation(
                        it,
                        Messages.get(MSG, Messages[MSG_CONSTRUCTOR], Messages[MSG_FUNCTION]),
                    )
                }

            super.visitClassComplete(node)
        }
    }
}
