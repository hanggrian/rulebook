package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasAnnotation
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#built-in-function-position) */
public class BuiltInFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "BuiltInFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "built.in.function.position"

        private val BUILTIN_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        private fun MethodNode.isBuiltInFunction() =
            hasAnnotation("Override") && name in BUILTIN_FUNCTIONS
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // collect functions
            // in Groovy, static members have specific keyword
            val methods =
                node
                    .methods
                    .filterNot { it.isStatic }

            for ((i, method) in methods.withIndex()) {
                // target special function
                method
                    .takeIf { it.isBuiltInFunction() }
                    ?: continue

                // checks for violation
                methods
                    .subList(i, methods.size)
                    .takeIf { nodes -> nodes.any { !it.isBuiltInFunction() } }
                    ?: continue
                addViolation(method, Messages.get(MSG, method.name))
            }
        }
    }
}
