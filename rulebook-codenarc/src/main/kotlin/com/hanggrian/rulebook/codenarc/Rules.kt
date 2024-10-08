package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.imports.AbstractImportRule

/** A CodeNarc rule with immutable name and priority. */
public abstract class Rule : AbstractAstVisitorRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** An alias of base import rule. */
public abstract class ImportRule : AbstractImportRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}
