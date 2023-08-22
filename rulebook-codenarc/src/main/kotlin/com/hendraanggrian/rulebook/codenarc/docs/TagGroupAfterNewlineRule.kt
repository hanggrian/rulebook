package com.hendraanggrian.rulebook.codenarc.docs

import com.hendraanggrian.rulebook.codenarc.Messages
import com.hendraanggrian.rulebook.codenarc.RulebookLinedRule
import org.codenarc.rule.Violation

/**
 * [See wiki](https://github.com/hendraanggrian/rulebook/wiki/TagGroupAfterNewline).
 */
class TagGroupAfterNewlineRule : RulebookLinedRule() {
    private companion object {
        const val MSG = "tag.group.after.newline"
    }

    override var title: String = "TagGroupAfterNewline"

    override fun visitLine(lineNo: Int, line: String, violations: MutableList<Violation>) {
        // first line of filter
        val lineTrimmed = line.trimStart()
        if (!lineTrimmed.startsWith("/**")) {
            return
        }

        // collect whole comment without first and last line
        val comments = mutableListOf<String>()
        var endLineNo = lineNo + 1
        while (!lines[endLineNo].trimStart().startsWith("*/")) {
            comments += lines[endLineNo]
            endLineNo++
        }

        // find first tag
        val firstTagLine = comments.first { it.trimStart().startsWith("* @") }
        val firstTagLineNo = comments.indexOf(firstTagLine)

        // check if previous tag is newline
        val previousTrimmed = comments[firstTagLineNo - 1].trim()
        if (previousTrimmed != "/**" && previousTrimmed != "*") {
            violations += Violation().also {
                it.rule = this@TagGroupAfterNewlineRule
                it.lineNumber = lineNo
                it.sourceLine = firstTagLine
                it.message = Messages[MSG]
            }
        }
    }
}
