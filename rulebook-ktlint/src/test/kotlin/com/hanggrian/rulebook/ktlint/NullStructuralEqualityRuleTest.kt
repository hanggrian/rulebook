package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.NullStructuralEqualityRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class NullStructuralEqualityRuleTest {
    private val assertThatCode = assertThatRule { NullStructuralEqualityRule() }

    @Test
    fun `Rule properties`() = NullStructuralEqualityRule().assertProperties()

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
            LintViolation(2, 13, Messages.get(MSG, "==")),
            LintViolation(3, 20, Messages.get(MSG, "!=")),
        )
}
