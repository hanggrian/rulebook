package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class IllegalClassNameSuffixRuleTest {
    private val assertThatCode = assertThatRule { IllegalClassNameSuffixRule() }

    @Test
    fun `Rule properties`() = IllegalClassNameSuffixRule().assertProperties()

    @Test
    fun `Meaningful class names`() =
        assertThatCode(
            """
            class Spaceship

            annotation class Rocket

            data class Navigator

            sealed class Planet

            interface Route

            object Logger
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Meaningless class names`() =
        assertThatCode(
            """
            class SpaceshipManager

            annotation class RocketManager

            data class NavigationManager

            sealed class PlanetManager

            interface RouteManager

            object LoggerManager
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 7, "Avoid meaningless word 'Manager'."),
            LintViolation(3, 18, "Avoid meaningless word 'Manager'."),
            LintViolation(5, 12, "Avoid meaningless word 'Manager'."),
            LintViolation(7, 14, "Avoid meaningless word 'Manager'."),
            LintViolation(9, 11, "Avoid meaningless word 'Manager'."),
            LintViolation(11, 8, "Avoid meaningless word 'Manager'."),
        )

    @Test
    fun `Utility class found`() =
        assertThatCode(
            """
            class SpaceshipUtil
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 7, "Rename utility class to 'Spaceships'.")

    @Test
    fun `Utility file found`() =
        assertThatCode("")
            .asFileWithPath("/some/path/SpaceshipUtility.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Rename utility class to 'Spaceships'.")
}
