package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.hasAnnotation
import com.hanggrian.rulebook.codenarc.rules.CommonFunctionPositionRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.rules.CommonFunctionPositionRule.Companion.isBuiltInFunction
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#common-function-position) */
public class CommonFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "CommonFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = CommonFunctionPositionVisitor::class.java

    internal companion object {
        const val MSG = "common.function.position"

        private val COMMON_FUNCTIONS =
            hashSetOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        fun MethodNode.isBuiltInFunction() = hasAnnotation("Override") && name in COMMON_FUNCTIONS
    }
}

public class CommonFunctionPositionVisitor : RulebookVisitor() {
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
