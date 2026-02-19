package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class MeaninglessWordRuleTest {
    private val assertThatCode = assertThatRule { MeaninglessWordRule() }

    @Test
    fun `Rule properties`() = MeaninglessWordRule().assertProperties()

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
    fun `Allow meaningless prefix`() =
        assertThatCode(
            """
            class WrapperSpaceship

            annotation class WrapperRocket

            data class WrapperNavigation

            sealed class WrapperPlanet

            interface WrapperRoute

            object WrapperLogger
            """.trimIndent(),
        ).hasNoLintViolations()
}
