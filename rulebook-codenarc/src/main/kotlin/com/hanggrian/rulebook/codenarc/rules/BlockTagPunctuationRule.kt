package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.Messages
import com.hanggrian.rulebook.codenarc.properties.ConfigurableTags
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation) */
public class BlockTagPunctuationRule :
    RulebookFileRule(),
    ConfigurableTags {
    override val tagSet: HashSet<String> = hashSetOf("@param", "@return")

    override fun getName(): String = "BlockTagPunctuation"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>) {
        // checks for violation
        violations +=
            buildRegex(tagSet)
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

        val END_PUNCTUATIONS = hashSetOf('.', '!', '?', ')')

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
