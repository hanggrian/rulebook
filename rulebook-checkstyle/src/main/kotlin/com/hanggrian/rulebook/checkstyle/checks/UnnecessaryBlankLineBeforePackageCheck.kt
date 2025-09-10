package com.hanggrian.rulebook.checkstyle.checks

import com.hanggrian.rulebook.checkstyle.Messages
import com.puppycrawl.tools.checkstyle.api.FileText
import java.io.File

/** [See detail](https://hanggrian.github.io/rulebook/rules/#unnecessary-blank-line-before-package) */
public class UnnecessaryBlankLineBeforePackageCheck : RulebookFileCheck() {
    override fun processFiltered(file: File, fileText: FileText) {
        super.processFiltered(file, fileText)

        // checks for violation
        lines
            .firstOrNull()
            ?.takeIf { it.isBlank() }
            ?: return
        log(1, Messages[MSG])
    }

    private companion object {
        const val MSG = "unnecessary.blank.line.before.package"
    }
}
