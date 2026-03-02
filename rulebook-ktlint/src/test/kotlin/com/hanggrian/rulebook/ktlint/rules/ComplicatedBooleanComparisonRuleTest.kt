package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ComplicatedBooleanComparisonRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ComplicatedBooleanComparisonRule() }

    @Test
    fun `Rule properties`() = ComplicatedBooleanComparisonRule().assertProperties()

    @Test
    fun `Boolean constant condition`() =
        assertThatCode(
            """
            fun foo(val foo: Boolean) {
                if (foo) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Boolean expression condition`() =
        assertThatCode(
            """
            fun foo(val foo: Boolean) {
                if (foo == true) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 16, "Remove boolean constant.")

    @Test
    fun `Duplicate negation constant`() =
        assertThatCode(
            """
            fun foo(val foo: Boolean) {
                if (!!foo) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 9, "Remove duplicate negation."),
        )

    @Test
    fun `Skip nullable property`() =
        assertThatCode(
            """
            fun foo(val foo: Foo) {
                if (foo?.foo == true) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
