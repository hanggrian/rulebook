package com.hendraanggrian.rulebook.codenarc.docs

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.RulebookLinedRule
import org.codenarc.rule.Violation

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/TagDescriptionPunctuation).
 */
class TagDescriptionPunctuationRule : RulebookLinedRule() {
    private companion object {
        const val MSG = "tag.description.punctuation"

        private val END_PUNCTUATIONS = setOf('.', '?', '!')
    }

    override var title: String = "TagDescriptionPunctuation"

    override fun visitLine(lineNo: Int, line: String, violations: MutableList<Violation>) {
        val lineTrimmed = line.trimStart()

        // only enforce certain tags
        val tagType = when {
            lineTrimmed.startsWith("* @param") -> "@param"
            lineTrimmed.startsWith("* @return") -> "@return"
            lineTrimmed.startsWith("* @throws") -> "@throws"
            else -> return
        }

        // check multiline declaration
        val targetLine = when {
            lines[lineNo + 1].isContinuation() -> {
                var lastContinuationLineNo = lineNo + 1
                while (lines[lastContinuationLineNo + 1].isContinuation()) {
                    lastContinuationLineNo++
                }
                lines[lastContinuationLineNo]
            }
            else -> line
        }

        // check the suffix
        val punctuation = targetLine.trimComment().trimEnd().lastOrNull() ?: return
        if (punctuation !in END_PUNCTUATIONS) {
            violations += Violation().also {
                it.rule = this@TagDescriptionPunctuationRule
                it.lineNumber = lineNo
                it.sourceLine = lineTrimmed.substringAfter("* ")
                it.message = Messages.get(MSG, tagType)
            }
        }
    }

    private fun String.isContinuation(): Boolean {
        val trimmed = trimStart()
        return !trimmed.startsWith("* @") && !trimmed.startsWith("*/")
    }

    private fun String.trimComment(): String {
        val index = indexOf("//")
        if (index == -1) {
            return this
        }
        return substring(0, index).trimEnd()
    }
}
