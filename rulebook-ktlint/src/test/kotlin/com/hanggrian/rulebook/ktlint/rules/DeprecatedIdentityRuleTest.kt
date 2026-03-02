package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DeprecatedIdentityRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { DeprecatedIdentityRule() }

    @Test
    fun `Rule properties`() = DeprecatedIdentityRule().assertProperties()

    @Test
    fun `Constant structural equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo == null) {
                } else if (foo != true) {
                } else if (foo == 1.0f) {
                } else if (foo != 'a') {
                } else if (foo == 0) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Constant referential equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo === null) {
                } else if (foo !== true) {
                } else if (foo === 1.0f) {
                } else if (foo !== 'a') {
                } else if (foo === 0) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, "Use operator '=='."),
            LintViolation(3, 20, "Use operator '!='."),
            LintViolation(4, 20, "Use operator '=='."),
            LintViolation(5, 20, "Use operator '!='."),
            LintViolation(6, 20, "Use operator '=='."),
        )

    @Test
    fun `Target chained condition`() =
        assertThatCode(
            """
            fun baz() {
                if (foo === null || foo === true) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, "Use operator '=='."),
            LintViolation(2, 29, "Use operator '=='."),
        )
}
