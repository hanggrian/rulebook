package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import groovy.lang.Closure
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See wiki](https://github.com/hanggrian/rulebook/wiki/Rules/#import-ordering) */
public class ImportOrderingRule : RulebookImportRule() {
    override fun getName(): String = "ImportOrdering"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>): Unit =
        eachImportLine(
            sourceCode,
            object : Closure<Unit>(null) {
                var prevStatic: Boolean? = null
                var prevIndex: Int? = null
                var prevDirective: String? = null

                @Suppress("unused")
                fun doCall(vararg arguments: Any) {
                    // distinguish between standard and static imports
                    val index = arguments.first() as Int
                    val line = arguments.last() as String
                    val (directive, isStatic) =
                        when {
                            line.matches(IMPORT_REGEX) -> line.substringAfter("import ") to false
                            line.matches(STATIC_IMPORT_REGEX) ->
                                line.substringAfter("import static ") to true
                            else -> return
                        }

                    // skip first index
                    if (isStatic != prevStatic || (prevIndex == null && prevDirective == null)) {
                        prevStatic = isStatic
                        prevIndex = index
                        prevDirective = directive
                        return
                    }

                    // checks for violation
                    if (index != prevIndex!! + 1) {
                        violations.add(violationOf(index, line, Messages.get(MSG_JOIN, directive)))
                    }
                    if (directive < prevDirective!!) {
                        violations.add(
                            violationOf(
                                index,
                                line,
                                Messages.get(MSG_SORT, directive, prevDirective!!),
                            ),
                        )
                    }

                    prevStatic = isStatic
                    prevIndex = index
                    prevDirective = directive
                }
            },
        )

    private fun violationOf(lineNumber: Int, line: String, message: String) =
        Violation().also {
            it.rule = this@ImportOrderingRule
            it.lineNumber = lineNumber
            it.sourceLine = line.trim()
            it.message = message
        }

    internal companion object {
        const val MSG_SORT = "import.ordering.sort"
        const val MSG_JOIN = "import.ordering.join"

        private val IMPORT_REGEX = Regex(NON_STATIC_IMPORT_PATTERN)
        private val STATIC_IMPORT_REGEX = Regex(STATIC_IMPORT_PATTERN)
    }
}
