package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.splitToList
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation) */
public class BlockTagPunctuationRule : RulebookFileRule() {
    internal var tagList = listOf("@param", "@return")

    public var tags: String
        get() = throw UnsupportedOperationException()
        set(value) {
            tagList = value.splitToList()
        }

    override fun getName(): String = "BlockTagPunctuation"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            buildRegex(tagList)
                .findAll(sourceCode.text)
                .filter { ' ' in it.value && it.value.last() !in END_PUNCTUATIONS }
                .map {
                    val lineNumber = sourceCode.getLineNumberForCharacterIndex(it.range.last)
                    createViolation(
                        lineNumber,
                        sourceCode.line(lineNumber - 1),
                        Messages[MSG, it.value.substringBefore(' ')],
                    )
                }
    }

    internal companion object {
        const val MSG = "block.tag.punctuation"

        val END_PUNCTUATIONS = setOf('.', '!', '?', ')')

        fun buildRegex(tags: Collection<String>): Regex {
            val tagGroup =
                tags
                    .joinToString("|") {
                        when {
                            it.startsWith('@') -> it.substring(1)
                            else -> it
                        }
                    }.let { "@(?:$it)" }
            return Regex(
                tagGroup +
                    """[^\n]*(?:\n\s*\*(?!\s*@|/|\n).*)*""",
            )
        }
    }
}
