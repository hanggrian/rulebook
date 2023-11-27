package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ThrowNarrowerTypeRuleTest {
    private val assertThatCode = assertThatRule { ThrowNarrowerTypeRule() }

    @Test
    fun `Throw subclass exceptions`() =
        assertThatCode(
            """
            fun exception() {
                throw IllegalStateException()
            }
            fun error() {
                throw StackOverflowError()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Throw superclass exceptions`() =
        assertThatCode(
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
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 11, Messages[ThrowNarrowerTypeRule.MSG]),
            LintViolation(5, 11, Messages[ThrowNarrowerTypeRule.MSG]),
            LintViolation(8, 11, Messages[ThrowNarrowerTypeRule.MSG]),
        )

    @Test
    fun `Throwing exceptions by reference`() =
        assertThatCode(
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
            """.trimIndent(),
        ).hasNoLintViolations()
}
