package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.WordMeaningRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class WordMeaningRuleTest {
    private val assertThatCode = assertThatRule { WordMeaningRule() }

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
            class BaseSpaceship
            annotation class AbstractRocket
            data class NavigationHelper
            sealed class PlanetInfo
            interface RouteData
            object LogHelper
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 7, Messages.get(MSG, "Base")),
            LintViolation(2, 18, Messages.get(MSG, "Abstract")),
            LintViolation(3, 12, Messages.get(MSG, "Helper")),
            LintViolation(4, 14, Messages.get(MSG, "Info")),
            LintViolation(5, 11, Messages.get(MSG, "Data")),
            LintViolation(6, 8, Messages.get(MSG, "Helper")),
        )

    @Test
    fun `Violating both ends`() =
        assertThatCode(
            """
            class BaseSpaceshipManager
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 7, Messages.get(MSG, "Base")),
            LintViolation(1, 7, Messages.get(MSG, "Manager")),
        )

    @Test
    fun `Utility file`() =
        assertThatCode("")
            .asFileWithPath("/some/path/Util.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, Messages.get(MSG, "Util"))
}
