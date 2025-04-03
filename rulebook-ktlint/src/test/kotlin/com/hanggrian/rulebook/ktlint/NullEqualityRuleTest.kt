package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class NullEqualityRuleTest {
    private val assertThatCode = assertThatRule { NullEqualityRule() }

    @Test
    fun `Rule properties`() = NullEqualityRule().assertProperties()

    @Test
    fun `Null structural equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo == null) {
                } else if (foo != null) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Null referential equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo === null) {
                } else if (foo !== null) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, "Use operator '=='."),
            LintViolation(3, 20, "Use operator '!='."),
        )
}
