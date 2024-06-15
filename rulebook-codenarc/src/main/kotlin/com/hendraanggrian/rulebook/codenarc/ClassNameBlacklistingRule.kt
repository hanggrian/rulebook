package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.rule.AbstractAstVisitor

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/Rules#class-name-blacklisting)
 */
public class ClassNameBlacklistingRule : Rule() {
    private var names = "Util, Utility, Helper, Manager, Wrapper"

    public fun setNames(names: String) {
        this.names = names
    }

    override fun getName(): String = "ClassNameBlacklisting"

    override fun getAstVisitorClass(): Class<*> = Visitor::class.java

    internal companion object {
        const val MSG_ALL = "class.name.blacklisting.all"
        const val MSG_UTIL = "class.name.blacklisting.util"

        private val TITLE_CASE_REGEX =
            Regex("((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|([A-Z]+(?=([A-Z][a-z])|(\$)|([0-9]))))")
    }

    public class Visitor : AbstractAstVisitor() {
        override fun visitClassEx(node: ClassNode) {
            // checks for violation
            TITLE_CASE_REGEX.findAll(node.name)
                .filter { it.value in (rule as ClassNameBlacklistingRule).names.split(", ") }
                .forEach {
                    when (val word = it.value) {
                        "Util", "Utility" ->
                            addViolation(
                                node,
                                Messages.get(MSG_UTIL, node.name.substringBefore(word) + 's'),
                            )
                        else -> addViolation(node, Messages.get(MSG_ALL, word))
                    }
                }

            super.visitClassEx(node)
        }
    }
}
