package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ConfusingAssertionRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ConfusingAssertionRule() }

    @Test
    fun `Rule properties`() = ConfusingAssertionRule().assertProperties()

    @Test
    fun `Positive assertion calls`() =
        assertThatCode(
            """
            fun foo() {
                assertTrue(s.isEmpty())
            }
            """.trimIndent(),
        ).asTest()
            .hasNoLintViolations()

    @Test
    fun `Negative assertion calls`() =
        assertThatCode(
            """
            fun foo() {
                assertTrue(!s.isEmpty())
            }
            """.trimIndent(),
        ).asTest()
            .hasLintViolationWithoutAutoCorrect(
                2,
                5,
                "Omit negation and replace assertion with 'assertFalse'.",
            )

    @Test
    fun `Skip chained conditions`() =
        assertThatCode(
            """
            fun foo() {
                assertTrue(!s.isEmpty() && s.isBlank())
            }
            """.trimIndent(),
        ).asTest()
            .hasNoLintViolations()
}
