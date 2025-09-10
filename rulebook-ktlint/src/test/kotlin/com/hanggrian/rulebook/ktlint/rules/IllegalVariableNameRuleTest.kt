package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class IllegalVariableNameRuleTest {
    private val assertThatCode = assertThatRule { IllegalVariableNameRule() }

    @Test
    fun `Rule properties`() = IllegalVariableNameRule().assertProperties()

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
            LintViolation(1, 5, "Use descriptive name."),
            LintViolation(2, 5, "Use descriptive name."),
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
            LintViolation(1, 9, "Use descriptive name."),
            LintViolation(2, 25, "Use descriptive name."),
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
            LintViolation(2, 10, "Use descriptive name."),
            LintViolation(2, 15, "Use descriptive name."),
        )

    @Test
    fun `Find backticked identifier`() =
        assertThatCode(
            """
            class Foo {
                val `int` = 0
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 9, "Use descriptive name.")
}
