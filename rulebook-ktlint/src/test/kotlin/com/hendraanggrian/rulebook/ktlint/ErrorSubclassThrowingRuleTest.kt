package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ErrorSubclassThrowingRuleTest {
    private val assertThatCode = assertThatRule { ErrorSubclassThrowingRule() }

    @Test
    fun `Rule properties`(): Unit = ErrorSubclassThrowingRule().assertProperties()

    @Test
    fun `Throw subclass exceptions`() =
        assertThatCode(
            """
            fun foo() {
                throw IllegalStateException()
            }

            fun bar() {
                throw StackOverflowError()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Throw superclass exceptions`() =
        assertThatCode(
            """
            fun foo() {
                throw Throwable()
            }

            fun bar() {
                throw Exception()
            }

            fun baz() {
                throw Error()
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 11, Messages[ErrorSubclassThrowingRule.MSG]),
            LintViolation(6, 11, Messages[ErrorSubclassThrowingRule.MSG]),
            LintViolation(10, 11, Messages[ErrorSubclassThrowingRule.MSG]),
        )

    @Test
    fun `Skip throwing by reference`() =
        assertThatCode(
            """
            fun foo() {
                val throwable = Throwable()
                throw throwable
            }
            fun bar() {
                val error = Error()
                throw error
            }
            fun baz() {
                val exception = Exception()
                throw exception
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
