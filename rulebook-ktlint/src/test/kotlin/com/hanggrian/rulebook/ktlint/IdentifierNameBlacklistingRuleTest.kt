package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.IdentifierNameBlacklistingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class IdentifierNameBlacklistingRuleTest {
    private val assertThatCode = assertThatRule { IdentifierNameBlacklistingRule() }

    @Test
    fun `Rule properties`() = IdentifierNameBlacklistingRule().assertProperties()

    @Test
    fun `Descriptive property names`() =
        assertThatCode(
            """
            val age = 0
            val name: String = ""
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Prohibited property names`() =
        assertThatCode(
            """
            val int = 0
            val string: String = ""
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 5, Messages[MSG]),
            LintViolation(2, 5, Messages[MSG]),
        )

    @Test
    fun `Descriptive parameter names`() =
        assertThatCode(
            """
            fun foo(age: Int) {
                listOf(0).forEach { name ->
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Prohibited parameter names`() =
        assertThatCode(
            """
            fun foo(int: Int) {
                listOf(0).forEach { string ->
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 9, Messages[MSG]),
            LintViolation(2, 25, Messages[MSG]),
        )

    @Test
    fun `Descriptive de-structuring property names`() =
        assertThatCode(
            """
            fun foo() {
                val (age, name) = kotlin.Pair(0, "")
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Prohibited de-structuring property names`() =
        assertThatCode(
            """
            fun foo() {
                val (int, string) = kotlin.Pair(0, "")
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 10, Messages[MSG]),
            LintViolation(2, 15, Messages[MSG]),
        )

    @Test
    fun `Find backticked identifier`() =
        assertThatCode(
            """
            class Foo {
                val `int` = 0
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 9, Messages[MSG])
}
