package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class IllegalThrowRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { IllegalThrowRule() }

    @Test
    fun `Rule properties`() = IllegalThrowRule().assertProperties()

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
            LintViolation(2, 11, "Throw a narrower type."),
            LintViolation(6, 11, "Throw a narrower type."),
            LintViolation(10, 11, "Throw a narrower type."),
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
