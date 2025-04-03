package com.hanggrian.rulebook.ktlint

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
            LintViolation(4, 7, "Omit redundant 'else' condition."),
            LintViolation(6, 7, "Omit redundant 'else' condition."),
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
            LintViolation(3, 5, "Omit redundant 'else' condition."),
            LintViolation(4, 5, "Omit redundant 'else' condition."),
        )
}
