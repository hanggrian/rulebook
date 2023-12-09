package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.PropertyIdiomaticNamingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class PropertyIdiomaticNamingRuleTest {
    private val assertThatCode = assertThatRule { PropertyIdiomaticNamingRule() }

    @Test
    fun `Descriptive names`() =
        assertThatCode(
            """
            val name: String = ""

            val text: List<String> = emptyList()
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Class names`() =
        assertThatCode(
            """
            val string: String = ""

            val list: List<String> = emptyList()
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 5, Messages[MSG]),
            LintViolation(3, 5, Messages[MSG]),
        )

    @Test
    fun `Skip no type declaration`() =
        assertThatCode(
            """
            val string = ""

            val list = emptyList<String>()
            """.trimIndent(),
        ).hasNoLintViolations()
}
