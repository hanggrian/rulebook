package com.hendraanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule

/** Abstract rule wrapper. */
abstract class RulebookNarc : AbstractAstVisitorRule() {
    abstract var title: String
    open var level: Int = 3

    override fun getName(): String = title
    override fun setName(name: String) {
        title = name
    }

    override fun getPriority(): Int = level
    override fun setPriority(priority: Int) {
        level = priority
    }
}
