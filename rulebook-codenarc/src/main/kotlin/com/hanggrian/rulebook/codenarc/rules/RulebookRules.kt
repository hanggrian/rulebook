package com.hanggrian.rulebook.codenarc.rules

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractRule
import org.codenarc.rule.imports.AbstractImportRule

/** Rule that uses Groovy AST tree to validate a node. */
public abstract class RulebookAstRule : AbstractAstVisitorRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that validates an import statement. */
public abstract class RulebookImportRule : AbstractImportRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that checks source code by the raw file. */
public abstract class RulebookFileRule : AbstractRule() {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}
