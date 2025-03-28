package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#duplicate-blank-line-in-comment) */
public class DuplicateBlankLineInCommentRule : RulebookRule() {
    override fun getName(): String = "DuplicateBlankLineInComment"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(sourceCode.text)
                .map {
                    violationOf(
                        sourceCode.getLineNumberForCharacterIndex(it.range.last),
                        "//",
                        Messages[MSG],
                    )
                }
    }

    internal companion object {
        const val MSG = "duplicate.blank.line.in.comment"

        private val REGEX = Regex("//\\s*(?=//\\n)")
    }
}
