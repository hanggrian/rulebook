package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment) */
public class DuplicateBlankLineInCommentRule : RulebookFileRule() {
    override fun getName(): String = "DuplicateBlankLineInComment"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(sourceCode.text)
                .map {
                    createViolation(
                        sourceCode.getLineNumberForCharacterIndex(it.range.last),
                        "//",
                        Messages[MSG],
                    )
                }
    }

    internal companion object {
        const val MSG = "duplicate.blank.line.in.comment"

        val REGEX = Regex("""//\s*(?=//\n)""")
    }
}
