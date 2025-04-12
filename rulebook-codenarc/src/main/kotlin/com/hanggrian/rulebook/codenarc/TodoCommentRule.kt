package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode
import kotlin.text.RegexOption.IGNORE_CASE

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-indentation) */
public class TodoCommentRule : RulebookFileRule() {
    override fun getName(): String = "TodoComment"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            KEYWORD_REGEX
                .findAll(sourceCode.text)
                .map {
                    val lineNumber = sourceCode.getLineNumberForCharacterIndex(it.range.last)
                    createViolation(
                        lineNumber,
                        sourceCode.line(lineNumber - 1),
                        Messages.get(MSG_KEYWORD, it.value.substringAfterLast(' ')),
                    )
                } +
            SEPARATOR_REGEX
                .findAll(sourceCode.text)
                .map {
                    val lineNumber = sourceCode.getLineNumberForCharacterIndex(it.range.last)
                    createViolation(
                        lineNumber,
                        sourceCode.line(lineNumber - 1),
                        Messages.get(MSG_SEPARATOR, it.value.last()),
                    )
                }
    }

    private companion object {
        const val MSG_KEYWORD = "todo.comment.keyword"
        const val MSG_SEPARATOR = "todo.comment.separator"

        const val ANY_COMMENT = """\s*(\*|//)\s*(.*)\b"""

        val KEYWORD_REGEX = Regex("""$ANY_COMMENT\b(?i:fixme|todo)(?<!FIXME|TODO)\b""")
        val SEPARATOR_REGEX = Regex("""$ANY_COMMENT\b(todo|fixme)\S""", IGNORE_CASE)
    }
}
