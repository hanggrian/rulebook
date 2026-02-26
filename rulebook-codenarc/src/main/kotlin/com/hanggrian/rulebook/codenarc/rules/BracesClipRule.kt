package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#braces-clip) */
public class BracesClipRule : RulebookFileRule() {
    override fun getName(): String = "BracesClip"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(code.text)
                .map {
                    val lineNumber = code.getLineNumberForCharacterIndex(it.range.first)
                    createViolation(lineNumber, code.line(lineNumber - 1), Messages[MSG])
                }
    }

    internal companion object {
        const val MSG = "braces.clip"

        val REGEX =
            Regex(
                """(?<!\b(?:try|catch|do|if|else\sif|else)\b\s*(?:\([^)]*\)\s*)?)""" +
                    """\{\s+}""",
            )
    }
}
