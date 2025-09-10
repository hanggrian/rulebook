package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-tags-clip) */
public class EmptyTagsClipRule : RulebookFileRule() {
    override fun getName(): String = "EmptyTagsClip"

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
        const val MSG = "empty.tags.clip"

        val REGEX = Regex("""<\s+>""")
    }
}
