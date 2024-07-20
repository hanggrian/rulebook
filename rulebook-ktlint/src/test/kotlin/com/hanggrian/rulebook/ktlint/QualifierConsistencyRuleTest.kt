package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.QualifierConsistencyRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class QualifierConsistencyRuleTest {
    private val assertThatCode = assertThatRule { QualifierConsistencyRule() }

    @Test
    fun `Rule properties`() = QualifierConsistencyRule().assertProperties()

    @Test
    fun `Consistent class qualifiers`() =
        assertThatCode(
            """
            import kotlin.String

            val property: String = "Hello World"

            fun parameter(param: String) {}

            fun String.receiver() {}

            fun call() = String.format("%s", "Hello World")
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Redundant class qualifiers`() =
        assertThatCode(
            """
            import kotlin.String

            val property: kotlin.String = "Hello World"

            fun parameter(param: kotlin.String) {}

            fun kotlin.String.receiver() {}

            fun call() = kotlin.String.format("%s", "Hello World")
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 15, Messages[MSG]),
            LintViolation(5, 22, Messages[MSG]),
            LintViolation(7, 5, Messages[MSG]),
            LintViolation(9, 14, Messages[MSG]),
        )

    @Test
    fun `Consistent method qualifiers`() =
        assertThatCode(
            """
            import kotlin.String
            import kotlin.String.format

            val property = String.format("%s", "Hello World")

            fun call() = format("%s", "Hello World")
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Redundant method qualifiers`() =
        assertThatCode(
            """
            import kotlin.String.format

            val property = kotlin.String.format("%s", "Hello World")

            fun call() = kotlin.String.format("%s", "Hello World")
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 16, Messages[MSG]),
            LintViolation(5, 14, Messages[MSG]),
        )
}
