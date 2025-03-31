package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import groovy.lang.Closure
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#import-order) */
public class ImportOrderRule : RulebookImportRule() {
    override fun getName(): String = "ImportOrder"

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
                        violations +=
                            Violation().apply {
                                rule = this@ImportOrderRule
                                lineNumber = index
                                sourceLine = line
                                message = Messages.get(MSG_JOIN, directive)
                            }
                    }
                    if (directive < prevDirective!!) {
                        violations +=
                            Violation().apply {
                                rule = this@ImportOrderRule
                                lineNumber = index
                                sourceLine = line
                                message = Messages.get(MSG_SORT, directive, prevDirective!!)
                            }
                    }

                    prevStatic = isStatic
                    prevIndex = index
                    prevDirective = directive
                }
            },
        )

    internal companion object {
        const val MSG_SORT = "import.order.sort"
        const val MSG_JOIN = "import.order.join"

        private val IMPORT_REGEX = Regex(NON_STATIC_IMPORT_PATTERN)
        private val STATIC_IMPORT_REGEX = Regex(STATIC_IMPORT_PATTERN)
    }
}
