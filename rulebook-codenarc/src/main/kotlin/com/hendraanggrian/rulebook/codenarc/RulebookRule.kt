package com.hendraanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule

/**
 * A CodeNarc rule with immutable name and priority.
 */
public sealed class RulebookRule : AbstractAstVisitorRule() {
    public override fun getPriority(): Int = 3

    public final override fun setName(name: String?): Nothing =
        throw UnsupportedOperationException()

    public final override fun setPriority(priority: Int): Nothing =
        throw UnsupportedOperationException()
}
