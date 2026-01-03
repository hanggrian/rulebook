package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-braces-clip) */
public class EmptyBracesClipRule : RulebookFileRule() {
    override fun getName(): String = "EmptyBracesClip"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(sourceCode.text)
                .map {
                    val lineNumber = sourceCode.getLineNumberForCharacterIndex(it.range.first)
                    createViolation(lineNumber, sourceCode.line(lineNumber - 1), Messages[MSG])
                }
    }

    internal companion object {
        const val MSG = "empty.braces.clip"

        val REGEX =
            Regex(
                """(?<!\b(?:try|catch|do|if|else\sif|else)\b\s*(?:\([^)]*\)\s*)?)""" +
                    """\{\s+}""",
            )
    }
}
