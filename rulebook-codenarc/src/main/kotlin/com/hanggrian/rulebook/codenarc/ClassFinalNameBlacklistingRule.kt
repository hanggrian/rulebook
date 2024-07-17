package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#class-final-name-blacklisting)
 */
public class ClassFinalNameBlacklistingRule : Rule() {
    internal var names = setOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public fun setNames(names: String) {
        this.names = names.split(", ").toSet()
    }

    override fun getName(): String = "ClassFinalNameBlacklisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_ALL = "class.final.name.blacklisting.all"
        const val MSG_UTIL = "class.final.name.blacklisting.util"

        private val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            val finalName =
                (rule as ClassFinalNameBlacklistingRule)
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
