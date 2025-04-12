package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.internals.JAVADOC_LINE_PREFIX
import com.hanggrian.rulebook.codenarc.internals.JAVADOC_REGEX
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.Violation
import org.codenarc.source.SourceCode

/** [See detail](https://hanggrian.github.io/rulebook/rules/#block-tag-order) */
public class BlockTagOrderRule : RulebookFileRule() {
    override fun getName(): String = "BlockTagOrder"

    override fun applyTo(sourceCode: SourceCode, violations: MutableList<Violation>): Unit =
        JAVADOC_REGEX
            .findAll(sourceCode.text)
            .forEach { javadoc ->
                // collect block tags
                val blockTags =
                    REGEX
                        .findAll(javadoc.value)
                        .toList()

                // checks for violation
                for ((i, blockTag) in blockTags.withIndex()) {
                    val lastBlockTagName =
                        blockTags
                            .getOrNull(i - 1)
                            ?.value
                            ?.substringAfterLast(' ')
                            ?: continue
                    val blockTagName = blockTag.value.substringAfterLast(' ')
                    MEMBER_POSITIONS
                        .getOrDefault(lastBlockTagName, -1)
                        .takeIf { it > MEMBER_POSITIONS[blockTagName]!! }
                        ?: continue
                    val lineNumber =
                        sourceCode.getLineNumberForCharacterIndex(
                            javadoc.range.first +
                                blockTag.range.last,
                        )
                    violations +=
                        createViolation(
                            lineNumber,
                            sourceCode.line(lineNumber - 1),
                            Messages.get(MSG, blockTagName, lastBlockTagName),
                        )
                }
            }

    private companion object {
        const val MSG = "block.tag.order"

        val REGEX = Regex("""$JAVADOC_LINE_PREFIX@(?:param|return|throws|see)""")

        val MEMBER_POSITIONS =
            mapOf(
                "@param" to 0,
                "@return" to 1,
                "@throws" to 2,
                "@see" to 3,
            )
    }
}
