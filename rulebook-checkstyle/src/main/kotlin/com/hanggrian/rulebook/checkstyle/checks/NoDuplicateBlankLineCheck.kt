package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.puppycrawl.tools.checkstyle.api.FileText
import java.io.File

/** [See detail](https://hanggrian.github.io/rulebook/rules/#no-duplicate-blank-line) */
public class NoDuplicateBlankLineCheck : RulebookFileCheck() {
    override fun processFiltered(file: File, fileText: FileText) {
        super.processFiltered(file, fileText)

        // checks for violation
        var counter = 0
        for ((i, line) in lines.withIndex()) {
            counter = if (line.isBlank()) counter + 1 else 0
            if (counter < 2) {
                continue
            }
            log(i + 1, Messages[MSG])
        }
    }

    private companion object {
        const val MSG = "no.duplicate.blank.line"
    }
}
