package com.hendraanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule

/**
 * A CodeNarc rule with immutable name and priority.
 */
abstract class RulebookRule : AbstractAstVisitorRule() {
    override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}
