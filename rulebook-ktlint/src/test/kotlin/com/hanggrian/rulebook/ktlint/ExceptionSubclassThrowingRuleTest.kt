package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ExceptionSubclassThrowingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExceptionSubclassThrowingRuleTest {
    private val assertThatCode = assertThatRule { ExceptionSubclassThrowingRule() }

    @Test
    fun `Rule properties`(): Unit = ExceptionSubclassThrowingRule().assertProperties()

    @Test
    fun `Throw narrow exceptions`() =
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
    fun `Throw broad exceptions`() =
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
            LintViolation(2, 11, Messages[MSG]),
            LintViolation(6, 11, Messages[MSG]),
            LintViolation(10, 11, Messages[MSG]),
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
