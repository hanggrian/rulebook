package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ExceptionSubclassCatchingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExceptionSubclassCatchingRuleTest {
    private val assertThatCode = assertThatRule { ExceptionSubclassCatchingRule() }

    @Test
    fun `Rule properties`() = ExceptionSubclassCatchingRule().assertProperties()

    @Test
    fun `Catch narrow exceptions`() =
        assertThatCode(
            """
            fun foo() {
                try {
                } catch (e: IllegalStateException) {
                }
            }

            fun bar() {
                try {
                } catch (e: StackOverflowError) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Catch broad exceptions`() =
        assertThatCode(
            """
            fun foo() {
                try {
                } catch (e: Throwable) {
                }
            }

            fun bar() {
                try {
                } catch (e: Exception) {
                }
            }

            fun baz() {
                try {
                } catch (e: Error) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 17, Messages[MSG]),
            LintViolation(9, 17, Messages[MSG]),
            LintViolation(15, 17, Messages[MSG]),
        )
}
