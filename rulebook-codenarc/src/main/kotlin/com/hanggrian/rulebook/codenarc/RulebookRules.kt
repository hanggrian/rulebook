package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractRule
import org.codenarc.rule.Rule
import org.codenarc.rule.Violation
import org.codenarc.rule.imports.AbstractImportRule

/** Rule that uses Groovy AST tree to validate a node. */
public abstract class RulebookAstRule :
    AbstractAstVisitorRule(),
    ViolationCreator {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that validates an import statement. */
public abstract class RulebookImportRule :
    AbstractImportRule(),
    ViolationCreator {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

/** Rule that checks source code by the raw file. */
public abstract class RulebookRule :
    AbstractRule(),
    ViolationCreator {
    final override fun getPriority(): Int = 3

    final override fun setName(name: String?): Nothing = throw UnsupportedOperationException()

    final override fun setPriority(priority: Int): Nothing = throw UnsupportedOperationException()
}

private interface ViolationCreator : Rule {
    fun violationOf(lineNumber: Int, line: String, message: String) =
        Violation().also {
            it.rule = this
            it.lineNumber = lineNumber
            it.sourceLine = line.trim()
            it.message = message
        }
}
