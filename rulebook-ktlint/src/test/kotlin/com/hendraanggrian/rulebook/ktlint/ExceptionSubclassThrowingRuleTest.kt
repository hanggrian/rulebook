package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExceptionSubclassThrowingRuleTest {
    private val assertThatCode = assertThatRule { ExceptionSubclassThrowingRule() }

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
            LintViolation(2, 11, Messages[ExceptionSubclassThrowingRule.MSG]),
            LintViolation(5, 11, Messages[ExceptionSubclassThrowingRule.MSG]),
            LintViolation(8, 11, Messages[ExceptionSubclassThrowingRule.MSG]),
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
