package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.IllegalClassFinalNameRule.Companion.MSG_ALL
import com.hanggrian.rulebook.ktlint.IllegalClassFinalNameRule.Companion.MSG_UTIL
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class IllegalClassFinalNameRuleTest {
    private val assertThatCode = assertThatRule { IllegalClassFinalNameRule() }

    @Test
    fun `Rule properties`() = IllegalClassFinalNameRule().assertProperties()

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
            LintViolation(3, 18, Messages.get(MSG_ALL, "Manager")),
            LintViolation(5, 12, Messages.get(MSG_ALL, "Manager")),
            LintViolation(7, 14, Messages.get(MSG_ALL, "Manager")),
            LintViolation(9, 11, Messages.get(MSG_ALL, "Manager")),
            LintViolation(11, 8, Messages.get(MSG_ALL, "Manager")),
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
