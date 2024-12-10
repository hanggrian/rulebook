package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.PredicateCallPositivityRule.Companion.MSG_BINARY
import com.hanggrian.rulebook.ktlint.PredicateCallPositivityRule.Companion.MSG_PREFIX
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class PredicateCallPositivityRuleTest {
    private val assertThatCode = assertThatRule { PredicateCallPositivityRule() }

    @Test
    fun `Rule properties`() = PredicateCallPositivityRule().assertProperties()

    @Test
    fun `Positive predicate calls`() =
        assertThatCode(
            """
            fun foo() {
                "".takeUnless { it.isEmpty() }
            }

            fun bar() {
                listOf(1, 2).filterNot { it == 0 }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Negative predicate calls`() =
        assertThatCode(
            """
            fun foo() {
                "".takeIf { !it.isEmpty() }
            }

            fun bar() {
                listOf(1, 2).filter { it != 0 }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 8, Messages.get(MSG_PREFIX, "takeUnless")),
            LintViolation(6, 18, Messages.get(MSG_BINARY, "filterNot")),
        )

    @Test
    fun `Skip nested calls`() =
        assertThatCode(
            """
            fun foo() {
                "".takeIf { !it.isEmpty() && !it.isBlank() }
            }

            fun bar() {
                listOf(1, 2).filter { it != 0 && it > 0 }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
