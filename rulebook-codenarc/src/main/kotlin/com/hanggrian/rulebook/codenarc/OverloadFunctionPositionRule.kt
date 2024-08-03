package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#overload-function-position)
 */
public class OverloadFunctionPositionRule : Rule() {
    override fun getName(): String = "OverloadFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "overload.function.position"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassComplete(node: ClassNode) {
            super.visitClassComplete(node)

            val declaredIdentifiers = mutableSetOf<String>()
            var lastIdentifier: String? = null
            for (method in node.methods) {
                // checks for violation
                val name = method.name
                if (lastIdentifier != name && !declaredIdentifiers.add(name)) {
                    addViolation(method, Messages.get(MSG, name))
                }

                // keep variable instead iterating set until last
                lastIdentifier = name
            }
        }
    }
}
