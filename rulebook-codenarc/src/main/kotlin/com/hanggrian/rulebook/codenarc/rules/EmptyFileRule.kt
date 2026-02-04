package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#empty-file) */
public class EmptyFileRule : RulebookFileRule() {
    override fun getName(): String = "EmptyFile"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        for (line in sourceCode.lines) {
            if (line.trimStart().startsWith("package")) {
                continue
            }
            if (line.isNotBlank()) {
                return
            }
        }
        violations += createViolation(0, "", Messages[MSG])
    }

    internal companion object {
        const val MSG = "empty.file"
    }
}
