package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DokkaTest : AllRulesTest() {
    @Test
    fun org_jetbrains_dokka_gradle_DokkaExtension() {
        assertThatCodeAll(getCode("dokka/DokkaExtension.kt"))
            .hasLintViolationsForAdditionalRule(
                LintViolation(26, 1, "Convert into single-line.", false),
                LintViolation(313, 5, "Arrange member 'property' before 'function'.", false),
                LintViolation(327, 8, "Add blank line before block tag group.", false),
                LintViolation(333, 6, "Remove blank line after '/**'.", false),
            )
    }
}
