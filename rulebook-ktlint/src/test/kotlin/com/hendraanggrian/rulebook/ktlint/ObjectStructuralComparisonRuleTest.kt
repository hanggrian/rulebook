package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ObjectStructuralComparisonRule.Companion.MSG_EQ
import com.hendraanggrian.rulebook.ktlint.ObjectStructuralComparisonRule.Companion.MSG_NEQ
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ObjectStructuralComparisonRuleTest {
    private val assertThatCode = assertThatRule { ObjectStructuralComparisonRule() }

    @Test
    fun `Structural equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo == bar) {
                } else if (foo != bar) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Referential equalities`() =
        assertThatCode(
            """
            fun baz() {
                if (foo === bar) {
                } else if (foo !== bar) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, Messages[MSG_EQ]),
            LintViolation(3, 20, Messages[MSG_NEQ]),
        )
}
