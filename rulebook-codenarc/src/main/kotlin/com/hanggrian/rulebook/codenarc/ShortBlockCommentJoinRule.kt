package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.JAVADOC_REGEX
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#short-block-comment-join) */
public class ShortBlockCommentJoinRule : RulebookFileRule() {
    public var maxLineLength: Int = 100

    override fun getName(): String = "ShortBlockCommentJoin"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        for (javadoc in JAVADOC_REGEX.findAll(sourceCode.text)) {
            // no single-line or block tag
            val lineNumber = sourceCode.getLineNumberForCharacterIndex(javadoc.range.first)
            val lastLineNumber = sourceCode.getLineNumberForCharacterIndex(javadoc.range.last)
            val line =
                javadoc
                    .value
                    .takeIf { lastLineNumber - lineNumber == 2 }
                    ?.lines()
                    ?.get(1)
                    ?.takeUnless { '@' in it }
                    ?: continue

            // checks for violation
            val textLength =
                sourceCode.lines[lineNumber - 1].indexOf("/**") +
                    line.substringAfter('*').length
            textLength
                .takeIf { it + SINGLELINE_TEMPLATE.length <= maxLineLength }
                ?: continue
            violations +=
                createViolation(lineNumber, sourceCode.line(lineNumber - 1), Messages[MSG])
        }
    }

    private companion object {
        const val MSG = "short.block.comment.join"

        const val SINGLELINE_TEMPLATE = "/** */"
    }
}
