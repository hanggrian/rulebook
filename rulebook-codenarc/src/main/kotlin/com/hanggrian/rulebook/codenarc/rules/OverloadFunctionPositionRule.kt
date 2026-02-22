package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.rules.OverloadFunctionPositionRule.Companion.MSG
import org.codehaus.groovy.ast.ClassNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#overload-function-position) */
public class OverloadFunctionPositionRule : RulebookAstRule() {
    override fun getName(): String = "OverloadFunctionPosition"

    override fun getAstVisitorClass(): Class<*> = OverloadFunctionPositionVisitor::class.java

    internal companion object {
        const val MSG = "overload.function.position"
    }
}

public class OverloadFunctionPositionVisitor : RulebookVisitor() {
    override fun visitClassEx(node: ClassNode) {
        if (!isFirstVisit(node)) {
            return
        }

        // collect functions
        val functions =
            node
                .methods
                .filterNot { it.isStatic }

        // checks for violation
        val declaredIdentifiers = mutableSetOf<String>()
        for ((i, function) in functions.withIndex()) {
            val name =
                function
                    .name
                    .takeIf {
                        functions.getOrNull(i - 1)?.name != it &&
                            !declaredIdentifiers.add(it)
                    } ?: continue
            addViolation(function, Messages[MSG, name])
        }

        super.visitClassEx(node)
    }
}
