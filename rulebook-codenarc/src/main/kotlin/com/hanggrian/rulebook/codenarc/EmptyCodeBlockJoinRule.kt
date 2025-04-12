package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-code-block-join) */
public class EmptyCodeBlockJoinRule : RulebookFileRule() {
    override fun getName(): String = "EmptyCodeBlockJoin"

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
        const val MSG = "empty.code.block.join"

        val REGEX =
            Regex(
                """(?<!\b(?:try|catch|do|if|else\sif|else)\b\s*(?:\([^)]*\)\s*)?)""" +
                    """\{\s+}""",
            )
    }
}
