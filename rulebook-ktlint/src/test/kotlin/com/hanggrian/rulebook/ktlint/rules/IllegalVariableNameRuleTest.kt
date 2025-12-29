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

            val `height` = 0.0
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Prohibited property names`() =
        assertThatCode(
            """
            val int = 0
            val string: String = ""

            val `double` = 0.0
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 5, "Use descriptive name."),
            LintViolation(2, 5, "Use descriptive name."),
            LintViolation(4, 5, "Use descriptive name."),
        )

    @Test
    fun `Descriptive parameter names`() =
        assertThatCode(
            """
            fun foo(age: Int) {
                listOf(0).forEach { name ->
                }
            }

            fun bar(`height`: Double) {}
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

            fun bar(`double`: Double) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 9, "Use descriptive name."),
            LintViolation(2, 25, "Use descriptive name."),
            LintViolation(6, 9, "Use descriptive name."),
        )

    @Test
    fun `Descriptive de-structuring property names`() =
        assertThatCode(
            """
            fun foo() {
                val (age, name) = kotlin.Pair(0, "")

                val (`height`, `weight`) = kotlin.Pair(0.0, 0f)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Prohibited de-structuring property names`() =
        assertThatCode(
            """
            fun foo() {
                val (int, string) = kotlin.Pair(0, "")

                val (`double`, `float`) = kotlin.Pair(0.0, 0f)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 10, "Use descriptive name."),
            LintViolation(2, 15, "Use descriptive name."),
            LintViolation(4, 10, "Use descriptive name."),
            LintViolation(4, 20, "Use descriptive name."),
        )
}
