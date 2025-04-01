package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#overload-function-position) */
public class OverloadFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "OverloadFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG = "overload.function.position"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // collect functions
            val functions =
                node
                    .methods
                    .filterNot { it.isStatic }

            // checks for violation
            val declaredIdentifiers = mutableSetOf<String>()
            for ((i, function) in functions.withIndex()) {
                val name = function.name
                if (functions.getOrNull(i - 1)?.name != name &&
                    !declaredIdentifiers.add(name)
                ) {
                    addViolation(function, Messages.get(MSG, name))
                }
            }
        }
    }
}
