package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-indentation) */
public class BlockTagIndentationRule : RulebookFileRule() {
    override fun getName(): String = "BlockTagIndentation"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            REGEX
                .findAll(sourceCode.text)
                .map {
                    val lineNumber = sourceCode.getLineNumberForCharacterIndex(it.range.last)
                    createViolation(lineNumber, sourceCode.line(lineNumber - 1), Messages[MSG])
                }
    }

    private companion object {
        const val MSG = "block.tag.indentation"

        val REGEX =
            Regex(
                """(\* @\w+.*)""" +
                    """(?=\s*\n\s*\* (?!@| {4}\S))""",
            )
    }
}
