package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.splitToList
import com.hanggrian.rulebook.codenarc.visitors.RulebookVisitor
import org.codehaus.groovy.ast.ClassNode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#illegal-class-name-suffix) */
public class IllegalClassNameSuffixRule : RulebookAstRule() {
    internal var nameList = listOf("Util", "Utility", "Helper", "Manager", "Wrapper")

    public var names: String
        get() = throw UnsupportedOperationException()
        set(value) {
            nameList = value.splitToList()
        }

    override fun getName(): String = "IllegalClassNameSuffix"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    private companion object {
        const val MSG_ALL = "illegal.class.name.suffix.all"
        const val MSG_UTIL = "illegal.class.name.suffix.util"

        val UTILITY_FINAL_NAMES = setOf("Util", "Utility")
    }

    public class Visitor : RulebookVisitor() {
        override fun visitClassEx(node: ClassNode) {
            super.visitClassEx(node)

            // checks for violation
            val finalName =
                (rule as IllegalClassNameSuffixRule)
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
