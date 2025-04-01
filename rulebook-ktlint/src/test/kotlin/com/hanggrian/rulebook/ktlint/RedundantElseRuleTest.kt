package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.RedundantElseRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RedundantElseRuleTest {
    private val assertThatCode = assertThatRule { RedundantElseRule() }

    @Test
    fun `Rule properties`() = RedundantElseRule().assertProperties()

    @Test
    fun `No throw or return in if`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Lift else when if has return`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    throw Exception()
                } else if (false) {
                    return
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 7, Messages[MSG]),
            LintViolation(6, 7, Messages[MSG]),
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertThatCode(
            """
            fun foo() {
                if (true) throw Exception()
                else if (false) return
                else baz()
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 5, Messages[MSG]),
            LintViolation(4, 5, Messages[MSG]),
        )
}
