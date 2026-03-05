package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-whitespace) */
public class DuplicateWhitespaceRule : RulebookFileRule() {
    override fun getName(): String = "DuplicateWhitespace"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(code.text)
                .map {
                    val lineNumber = code.getLineNumberForCharacterIndex(it.range.last)
                    createViolation(lineNumber, code.line(lineNumber - 1), Messages[MSG])
                }
    }

    internal companion object {
        const val MSG = "duplicate.whitespace"

        val REGEX = Regex("""(?<=\S)(?<!\*)[ \t]{2,}""")
    }
}
