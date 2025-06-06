package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractRule
import org.codenarc.rule.Rule
import org.codenarc.rule.Violation
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

/** Alias of [AbstractRule.createViolation] that is not protected. */
public fun Rule.createViolation(lineNumber: Int, sourceLine: String, message: String): Violation =
    Violation().also {
        it.rule = this@createViolation
        it.lineNumber = lineNumber
        it.sourceLine = sourceLine.trim()
        it.message = message
    }
