package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-final-name) */
public class IllegalClassFinalNameRule : RulebookAstRule() {
    internal var names = setOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "IllegalClassFinalName"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_ALL = "illegal.class.final.name.all"
        const val MSG_UTIL = "illegal.class.final.name.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            val finalName =
                (rule as IllegalClassFinalNameRule)
                    .names
                    .singleOrNull { node.name.endsWith(it) }
                    ?: return
            if (finalName in UTILITY_FINAL_NAMES) {
                addViolation(
                    node,
                    Messages.get(MSG_UTIL, node.name.substringBefore(finalName) + 's'),
                )
                return
            }
            addViolation(node, Messages.get(MSG_ALL, finalName))
        }
    }
}
