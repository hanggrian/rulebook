package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.JAVADOC_LINE_PREFIX
import com.hanggrian.rulebook.codenarc.JAVADOC_START
import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#blank-line-before-block-tags) */
public class BlankLineBeforeBlockTagsRule : RulebookFileRule() {
    override fun getName(): String = "BlankLineBeforeBlockTags"

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
        const val MSG = "blank.line.before.block.tags"

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
