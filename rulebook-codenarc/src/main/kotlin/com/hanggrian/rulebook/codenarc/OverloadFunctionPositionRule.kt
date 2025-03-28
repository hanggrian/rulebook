package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#overload-function-position) */
public class OverloadFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "OverloadFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "overload.function.position"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            super.visitClassComplete(node)

            // collect functions
            val functions = node.methods.filterNot { it.isStatic }

            val declaredIdentifiers = mutableSetOf<String>()
            var lastIdentifier: String? = null
            for (function in functions) {
                // checks for violation
                val name = function.name
                if (lastIdentifier != name && !declaredIdentifiers.add(name)) {
                    addViolation(function, Messages.get(MSG, name))
                }

                // keep variable instead iterating set until last
                lastIdentifier = name
            }
        }
    }
}
