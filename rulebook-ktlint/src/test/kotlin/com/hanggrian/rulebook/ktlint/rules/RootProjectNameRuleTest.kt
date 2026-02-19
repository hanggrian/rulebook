package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RootProjectNameRuleTest {
    private val assertThatCode = assertThatRule { RootProjectNameRule() }

    @Test
    fun `Rule properties`() = RootProjectNameRule().assertProperties()

    @Test
    fun `Correct project name`() =
        assertThatCode(
            """
            rootProject.name = "my-project"
            """.trimIndent(),
        ).asFileWithPath("settings.gradle.kts")
            .hasNoLintViolations()

    @Test
    fun `Incorrect project name`() =
        assertThatCode(
            """
            rootProject.name = "my project"
            """.trimIndent(),
        ).asFileWithPath("settings.gradle.kts")
            .hasLintViolationWithoutAutoCorrect(
                1,
                21,
                "Root project name cannot contain space or special character.",
            )

    @Test
    fun `Missing project name`() =
        assertThatCode(
            """
            pluginManagement.repositories.mavenCentral()
            dependencyResolutionManagement.repositories.mavenCentral()
            """.trimIndent(),
        ).asFileWithPath("settings.gradle.kts")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Set the root project name.")
}
