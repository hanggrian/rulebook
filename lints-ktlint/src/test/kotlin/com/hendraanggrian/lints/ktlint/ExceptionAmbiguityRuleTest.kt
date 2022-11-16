package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.ExceptionAmbiguityRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ExceptionAmbiguityRuleTest {
    private val assertThatCode = assertThatRule { ExceptionAmbiguityRule() }

    @Test
    fun `Throw by instance (supertype)`() {
        assertThatCode(
            """
                fun main() {
                    throw Exception()
                    throw Error()
                    throw Throwable()

                    throw Exception("Some message")
                    throw Error("Some message")
                    throw Throwable("Some message")
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 11, ERROR_MESSAGE.format("Exception")),
            LintViolation(3, 11, ERROR_MESSAGE.format("Error")),
            LintViolation(4, 11, ERROR_MESSAGE.format("Throwable"))
        )
    }

    @Test
    fun `Throw by instance (subtype)`() {
        assertThatCode(
            """
                fun main() {
                    throw IllegalStateException()
                    throw StackOverflowError()
                }
            """.trimIndent()
        ).hasNoLintViolations()
    }
}
