package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ThrowAmbiguityRuleTest {
    private val assertThatCode = assertThatRule { ThrowAmbiguityRule() }

    //region Supertype
    @Test
    fun `Throw supertype without messages`() = assertThatCode(
        """
        fun main() {
            throw Exception()
            throw Error()
            throw Throwable()
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 11, Messages.get(ThrowAmbiguityRule.MSG, "Exception")),
        LintViolation(3, 11, Messages.get(ThrowAmbiguityRule.MSG, "Error")),
        LintViolation(4, 11, Messages.get(ThrowAmbiguityRule.MSG, "Throwable"))
    )

    @Test
    fun `Throw supertype with messages`() = assertThatCode(
        """
        fun main() {
            throw Exception("Some message")
            throw Error("Some message")
            throw Throwable("Some message")
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Subtype
    @Test
    fun `Throw subtype without messages`() = assertThatCode(
        """
        fun main() {
            throw IllegalStateException()
            throw StackOverflowError()
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Throw subtype with messages`() = assertThatCode(
        """
        fun main() {
            throw IllegalStateException("Some message")
            throw StackOverflowError("Some message")
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Reference
    @Test
    fun `Throw reference instead of call expression`() = assertThatCode(
        """
        fun main() {
            val exception = Exception()
            throw exception

            val error = Error()
            throw error

            val throwable = Throwable()
            throw throwable
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion
}
