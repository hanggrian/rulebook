package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.QualifierConsistencyRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class QualifierConsistencyRuleTest {
    private val assertThatCode = assertThatRule { QualifierConsistencyRule() }

    @Test
    fun `Rule properties`(): Unit = QualifierConsistencyRule().assertProperties()

    @Test
    fun `Consistent qualifiers`() =
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
    fun `Redundant qualifiers`() =
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
            LintViolation(4, 22, Messages[MSG]),
            LintViolation(5, 5, Messages[MSG]),
            LintViolation(6, 14, Messages[MSG]),
        )
}
