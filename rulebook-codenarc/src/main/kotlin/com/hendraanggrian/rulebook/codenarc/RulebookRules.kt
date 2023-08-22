package com.hendraanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** Abstract rule wrapper. */
abstract class RulebookRule : AbstractAstVisitorRule() {
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

/** Highly inefficient, a current workaround instead of regular expression matching. */
abstract class RulebookLinedRule : RulebookRule() {
    lateinit var lines: List<String>

    abstract fun visitLine(lineNo: Int, line: String, violations: MutableList<Violation>)

    final override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        lines = sourceCode.text.lines()
        for ((lineNo, line) in lines.withIndex()) {
            visitLine(lineNo, line, violations)
        }
    }
}
