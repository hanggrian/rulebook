package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ClassNameBlacklistingRule.Companion.MSG_ALL
import com.hendraanggrian.rulebook.ktlint.ClassNameBlacklistingRule.Companion.MSG_UTIL
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ClassNameBlacklistingRuleTest {
    private val assertThatCode = assertThatRule { ClassNameBlacklistingRule() }

    @Test
    fun `Rule properties`(): Unit = ClassNameBlacklistingRule().assertProperties()

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
            LintViolation(1, 7, Messages.get(MSG_ALL, "Manager")),
            LintViolation(2, 18, Messages.get(MSG_ALL, "Manager")),
            LintViolation(3, 12, Messages.get(MSG_ALL, "Manager")),
            LintViolation(4, 14, Messages.get(MSG_ALL, "Manager")),
            LintViolation(5, 11, Messages.get(MSG_ALL, "Manager")),
            LintViolation(6, 8, Messages.get(MSG_ALL, "Manager")),
        )

    @Test
    fun `Utility class found`() =
        assertThatCode(
            """
            class SpaceshipUtil
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 7, Messages.get(MSG_UTIL, "Spaceships"))

    @Test
    fun `Utility file found`() =
        assertThatCode("")
            .asFileWithPath("/some/path/SpaceshipUtility.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, Messages.get(MSG_UTIL, "Spaceships"))
}
