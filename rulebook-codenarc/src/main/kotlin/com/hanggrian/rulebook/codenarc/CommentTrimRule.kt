package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#comment-trim) */
public class CommentTrimRule : RulebookFileRule() {
    override fun getName(): String = "CommentTrim"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>): Unit =
        sourceCode
            .lines
            .withIndex()
            .filter { it.index != 0 && "//" in it.value }
            .forEach { (start, line) ->
                // continue if this comment is first line
                sourceCode
                    .line(start - 1)
                    .takeUnless { "//" in it }
                    ?: return

                // iterate to find last
                var end = start
                while ("//" in sourceCode.lines.getOrNull(end + 1).orEmpty()) {
                    end++
                }

                // no blank comment
                start
                    .takeUnless { it == end }
                    ?: return

                // checks for violation
                if (line.isEolCommentEmpty()) {
                    violations += createViolation(start + 1, line, Messages[MSG])
                }
                val endLine =
                    sourceCode
                        .line(end)
                        .takeIf { it.isEolCommentEmpty() }
                        ?: return
                violations += createViolation(end + 1, endLine, Messages[MSG])
            }

    private companion object {
        const val MSG = "comment.trim"

        fun String.isEolCommentEmpty() =
            substringBefore("//").isBlank() &&
                substringAfter("//").isBlank()
    }
}
