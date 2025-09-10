package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.JAVADOC_LINE_PREFIX
import com.hanggrian.rulebook.codenarc.JAVADOC_START
import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#missing-blank-line-before-block-tags) */
public class MissingBlankLineBeforeBlockTagsRule : RulebookFileRule() {
    override fun getName(): String = "MissingBlankLineBeforeBlockTags"

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
        const val MSG = "missing.blank.line.before.block.tags"

        val REGEX =
            Regex(
                JAVADOC_START +
                    JAVADOC_LINE_PREFIX +
                    """(?!\s*@)""" +
                    "(.+?)" +
                    "($JAVADOC_LINE_PREFIX@)",
            )
    }
}
