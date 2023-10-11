package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DescribeThrowRuleTest {
    private val assertThatCode = assertThatRule { DescribeThrowRule() }

    @Test
    fun `Throw exceptions with messages`() = assertThatCode(
        """
        fun throwable() {
            throw Throwable("Hello World")
        }
        fun exception() {
            throw Exception("Hello World")
        }
        fun error() {
            throw Error("Hello World")
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Throw exceptions without messages`() = assertThatCode(
        """
        fun throwable() {
            throw Throwable()
        }
        fun exception() {
            throw Exception()
        }
        fun error() {
            throw Error()
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 11, Messages[DescribeThrowRule.MSG]),
        LintViolation(5, 11, Messages[DescribeThrowRule.MSG]),
        LintViolation(8, 11, Messages[DescribeThrowRule.MSG])
    )

    @Test
    fun `Throw explicit exceptions with messages`() = assertThatCode(
        """
        fun exception() {
            throw IllegalStateException("Hello World")
        }
        fun error() {
            throw StackOverflowError("Hello World")
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Throw explicit exceptions without messages`() = assertThatCode(
        """
        fun exception() {
            throw IllegalStateException()
        }
        fun error() {
            throw StackOverflowError()
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Throwing exceptions by reference`() = assertThatCode(
        """
        fun throwable() {
            val throwable = Throwable()
            throw throwable
        }
        fun exception() {
            val error = Error()
            throw error
        }
        fun error() {
            val exception = Exception()
            throw exception
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
