package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.RemoveRedundantQualifierRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RemoveRedundantQualifierRuleTest {
    private val assertThatCode = assertThatRule { RemoveRedundantQualifierRule() }

    @Test
    fun `No redundant qualifier`() =
        assertThatCode(
            """
            import kotlin.String

            var property: String = "Hello World"
            fun parameter(param: String) {
            }
            fun String.receiver() {
            }
            fun call() {
                property = String.format("%s", "Hello World")
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Redundant qualifiers`() =
        assertThatCode(
            """
            import kotlin.String

            var property: kotlin.String = "Hello World"
            fun parameter(param: kotlin.String) {
            }
            fun kotlin.String.receiver() {
            }
            fun call() {
                property = kotlin.String.format("%s", "Hello World")
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 15, Messages[MSG]),
            LintViolation(4, 22, Messages[MSG]),
            LintViolation(6, 5, Messages[MSG]),
            LintViolation(9, 16, Messages[MSG]),
        )
}
