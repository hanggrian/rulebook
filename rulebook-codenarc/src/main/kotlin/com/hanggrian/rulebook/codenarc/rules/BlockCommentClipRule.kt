package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.JAVADOC_REGEX
import com.hanggrian.rulebook.codenarc.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-comment-clip) */
public class BlockCommentClipRule : RulebookFileRule() {
    public var maxLineLength: Int = 100

    override fun getName(): String = "BlockCommentClip"

    override fun applyTo(code: SourceCode, violations: MutableList<Violation>) {
        for (javadoc in JAVADOC_REGEX.findAll(code.text)) {
            // skip single-line or block tag
            val lineNumber = code.getLineNumberForCharacterIndex(javadoc.range.first)
            val lastLineNumber = code.getLineNumberForCharacterIndex(javadoc.range.last)
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
                code.lines[lineNumber - 1].indexOf("/**") +
                    line.substringAfter('*').length
            textLength
                .takeIf { it + SINGLELINE_TEMPLATE.length <= maxLineLength }
                ?: continue
            violations +=
                createViolation(lineNumber, code.line(lineNumber - 1), Messages[MSG])
        }
    }

    internal companion object {
        const val MSG = "block.comment.clip"

        const val SINGLELINE_TEMPLATE = "/** */"
    }
}
