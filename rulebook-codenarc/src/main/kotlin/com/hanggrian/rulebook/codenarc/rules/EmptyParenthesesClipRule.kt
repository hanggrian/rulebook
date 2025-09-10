package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-parentheses-clip) */
public class EmptyParenthesesClipRule : RulebookFileRule() {
    override fun getName(): String = "EmptyParenthesesClip"

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
        const val MSG = "empty.parentheses.clip"

        val REGEX = Regex("""\(\s+\)""")
    }
}
