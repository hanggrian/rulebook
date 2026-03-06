package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#line-feed) */
public class LineFeedRule : RulebookFileRule() {
    override fun getName(): String = "LineFeed"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        code
            .takeIf { it.text.endsWith("\r\n") || it.text.endsWith('\r') }
            ?: return
        val lines = code.lines
        violations += createViolation(lines.size, lines.last(), Messages[MSG])
    }

    internal companion object {
        const val MSG = "line.feed"
    }
}
