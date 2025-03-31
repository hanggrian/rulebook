package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#member-order) */
public class MemberOrderRule : RulebookAstRule() {
    override fun getName(): String = "MemberOrder"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "member.order"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // get indices of first members
            val firstConstructorIndex =
                node
                    .declaredConstructors
                    .minOfOrNull { it.lineNumber }
            val firstFunctionIndex =
                node
                    .methods
                    .filterNot { it.isStatic }
                    .minOfOrNull { it.lineNumber }

            // in Groovy, static members have specific keyword
            node
                .fields
                .filterNot { it.isStatic }
                .forEach {
                    // checks for violation
                    when {
                        firstConstructorIndex != null && it.lineNumber > firstConstructorIndex ->
                            addViolation(it, Messages.get(MSG, "property", "constructor"))

                        firstFunctionIndex != null && it.lineNumber > firstFunctionIndex ->
                            addViolation(it, Messages.get(MSG, "property", "function"))
                    }
                }

            // checks for violation
            firstFunctionIndex ?: return
            node
                .declaredConstructors
                .filter { it.lineNumber > firstFunctionIndex }
                .forEach { addViolation(it, Messages.get(MSG, "constructor", "function")) }
        }
    }
}
