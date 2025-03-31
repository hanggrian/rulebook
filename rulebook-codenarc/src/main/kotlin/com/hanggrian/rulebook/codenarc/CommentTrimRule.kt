package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/all/#comment-trim) */
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
                    .lines[start - 1]
                    .takeUnless { "//" in it }
                    ?: return

                // iterate to find last
                var end = start
                while ("//" in sourceCode.lines.getOrNull(end + 1).orEmpty()) {
                    end++
                }

                // skip blank comment
                start
                    .takeUnless { it == end }
                    ?: return

                // checks for violation
                if (line.isEolCommentEmpty()) {
                    violations +=
                        Violation().apply {
                            rule = this@CommentTrimRule
                            lineNumber = start + 1
                            sourceLine = line
                            message = Messages[MSG]
                        }
                }
                val endLine =
                    sourceCode
                        .lines[end]
                        .takeIf { it.isEolCommentEmpty() }
                        ?: return
                violations +=
                    Violation().apply {
                        rule = this@CommentTrimRule
                        lineNumber = end + 1
                        sourceLine = endLine
                        message = Messages[MSG]
                    }
            }

    internal companion object {
        const val MSG = "comment.trim"

        private fun String.isEolCommentEmpty() =
            substringBefore("//").isBlank() &&
                substringAfter("//").isBlank()
    }
}
