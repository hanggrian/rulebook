package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.AvoidMeaninglessWordRule.Companion.MSG_PREFIX
import com.hendraanggrian.rulebook.ktlint.AvoidMeaninglessWordRule.Companion.MSG_SUFFIX
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class AvoidMeaninglessWordRuleTest {
    private val assertThatCode = assertThatRule { AvoidMeaninglessWordRule() }

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
            LintViolation(1, 7, Messages.get(MSG_PREFIX, "Base")),
            LintViolation(2, 18, Messages.get(MSG_PREFIX, "Abstract")),
            LintViolation(3, 12, Messages.get(MSG_SUFFIX, "Helper")),
            LintViolation(4, 14, Messages.get(MSG_SUFFIX, "Info")),
            LintViolation(5, 11, Messages.get(MSG_SUFFIX, "Data")),
            LintViolation(6, 8, Messages.get(MSG_SUFFIX, "Helper")),
        )

    @Test
    fun `Violating both ends`() =
        assertThatCode(
            """
            class BaseSpaceshipManager
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 7, Messages.get(MSG_PREFIX, "Base")),
            LintViolation(1, 7, Messages.get(MSG_SUFFIX, "Manager")),
        )
}
