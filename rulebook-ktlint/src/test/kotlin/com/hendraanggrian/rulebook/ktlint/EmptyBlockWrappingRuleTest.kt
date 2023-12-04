package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.EmptyBlockWrappingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EmptyBlockWrappingRuleTest {
    private val assertThatCode = assertThatRule { EmptyBlockWrappingRule() }

    @Test
    fun `Wrapped empty block`() =
        assertThatCode(
            """
            fun wrapped() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unwrapped empty block`() =
        assertThatCode(
            """
            fun space() { }
            fun newLine() {
            }
            fun blankLine() {

            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, Messages[MSG]),
            LintViolation(2, 15, Messages[MSG]),
            LintViolation(4, 17, Messages[MSG]),
        )
}
