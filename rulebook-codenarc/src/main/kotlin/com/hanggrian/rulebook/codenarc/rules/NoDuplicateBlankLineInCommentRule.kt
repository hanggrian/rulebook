package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-duplicate-blank-line-in-comment) */
public class NoDuplicateBlankLineInCommentRule : RulebookFileRule() {
    override fun getName(): String = "NoDuplicateBlankLineInComment"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(code.text)
                .map {
                    createViolation(
                        code.getLineNumberForCharacterIndex(it.range.last),
                        "//",
                        Messages[MSG],
                    )
                }
    }

    internal companion object {
        const val MSG = "no.duplicate.blank.line.in.comment"

        val REGEX = Regex("""//\s*(?=//\n)""")
    }
}
