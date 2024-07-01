package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#class-final-name-blacklisting)
 */
public class ClassFinalNameBlacklistingRule : Rule() {
    private var names = "Util, Utility, Helper, Manager, Wrapper"

    public fun setNames(names: String) {
        this.names = names
    }

    override fun getName(): String = "ClassFinalNameBlacklisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_ALL = "class.final.name.blacklisting.all"
        const val MSG_UTIL = "class.final.name.blacklisting.util"
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            // checks for violation
            val finalName =
                (rule as ClassFinalNameBlacklistingRule)
                    .names
                    .split(", ")
                    .firstOrNull { node.name.endsWith(it) }
                    ?: return
            when (finalName) {
                "Util", "Utility" ->
                    addViolation(
                        node,
                        Messages.get(MSG_UTIL, node.name.substringBefore(finalName) + 's'),
                    )
                else -> addViolation(node, Messages.get(MSG_ALL, finalName))
            }

            super.visitClassEx(node)
        }
    }
}
