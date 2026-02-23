package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ScriptFileNameRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ScriptFileNameRule() }

    @Test
    fun `Rule properties`() = ScriptFileNameRule().assertProperties()

    @Test
    fun `kebab-case file name`() =
        assertThatCode("")
            .asScript("my-file.gradle.kts")
            .hasNoLintViolations()

    @Test
    fun `PascalCase file name`() =
        assertThatCode("")
            .asScript("MyFile.gradle.kts")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Rename file to 'my-file'.")

    @Test
    fun `camelCase file name`() =
        assertThatCode("")
            .asScript("myFile.gradle.kts")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Rename file to 'my-file'.")

    @Test
    fun `snake_case file name`() =
        assertThatCode("")
            .asScript("my_file.gradle.kts")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Rename file to 'my-file'.")
}
