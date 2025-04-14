package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-brackets-clip) */
public class EmptyBracketsClipRule : RulebookFileRule() {
    override fun getName(): String = "EmptyBracketsClip"

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

    private companion object {
        const val MSG = "empty.brackets.clip"

        val REGEX = Regex("""\[(\s+|\s+:\s+)]""")
    }
}
