package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRule
import org.codenarc.rule.Rule
import org.codenarc.rule.Violation

/** Alias of [AbstractRule.createViolation] that is not protected. */
internal fun Rule.createViolation(lineNumber: Int, sourceLine: String, message: String): Violation =
    Violation().also {
        it.rule = this@createViolation
        it.lineNumber = lineNumber
        it.sourceLine = sourceLine.trim()
        it.message = message
    }
