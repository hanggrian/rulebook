package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasAnnotation
import com.hanggrian.rulebook.codenarc.rules.BuiltInFunctionPositionRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.BuiltInFunctionPositionRule.Companion.isBuiltInFunction
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#built-in-function-position) */
public class BuiltInFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "BuiltInFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = BuiltInFunctionPositionVisitor::class.java

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

        fun MethodNode.isBuiltInFunction() = hasAnnotation("Override") && name in BUILTIN_FUNCTIONS
    }
}

public class BuiltInFunctionPositionVisitor : RulebookVisitor() {
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
                .takeIf { nodes ->
                    nodes.any { !it.isBuiltInFunction() }
                } ?: continue
            addViolation(method, Messages[MSG, method.name])
        }
    }
}
