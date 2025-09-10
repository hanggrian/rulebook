package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-final-name) */
public class IllegalClassFinalNameRule : RulebookAstRule() {
    public var names: String = "Util, Utility, Helper, Manager, Wrapper"

    internal val nameList get() = names.split(',').map { it.trim() }

    override fun getName(): String = "IllegalClassFinalName"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_ALL = "illegal.class.final.name.all"
        const val MSG_UTIL = "illegal.class.final.name.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }

    public class Visitor : RulebookVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            val finalName =
                (rule as IllegalClassFinalNameRule)
                    .nameList
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
