package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import com.hanggrian.rulebook.codenarc.internals.hasAnnotation
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#special-function-position)
 */
public class SpecialFunctionPositionRule : Rule() {
    override fun getName(): String = "SpecialFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "special.function.position"

        private val SPECIAL_FUNCTIONS =
            setOf(
                "toString",
                "hashCode",
                "equals",
                "clone",
                "finalize",
            )

        private fun MethodNode.isSpecialFunction() =
            hasAnnotation("Override") && name in SPECIAL_FUNCTIONS
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            var lastObjectOverriddenMethod: MethodNode? = null
            for (method in node.methods) {
                // target special method
                if (method.isSpecialFunction()) {
                    lastObjectOverriddenMethod = method
                    continue
                }

                // in Groovy, static members have specific keyword
                if (method.isStatic) {
                    continue
                }

                // checks for violation
                if (lastObjectOverriddenMethod == null ||
                    lastObjectOverriddenMethod.lineNumber >= method.lineNumber
                ) {
                    continue
                }
                addViolation(
                    lastObjectOverriddenMethod,
                    Messages.get(MSG, lastObjectOverriddenMethod.name),
                )
                return super.visitClassComplete(node)
            }

            super.visitClassComplete(node)
        }
    }
}
