package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.EmptyCodeBlockWrappingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EmptyCodeBlockWrappingRuleTest {
    private val assertThatCode = assertThatRule { EmptyCodeBlockWrappingRule() }

    @Test
    fun `Wrapped empty block`() =
        assertThatCode(
            """
            fun foo() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unwrapped empty block`() =
        assertThatCode(
            """
            fun foo() { }
            fun bar() {
            }
            fun baz() {

            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 11, Messages[MSG]),
            LintViolation(2, 11, Messages[MSG]),
            LintViolation(4, 11, Messages[MSG]),
        )
}
