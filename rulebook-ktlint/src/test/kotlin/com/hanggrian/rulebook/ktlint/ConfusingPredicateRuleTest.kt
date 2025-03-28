package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ConfusingPredicateRule.Companion.MSG_EQUALS
import com.hanggrian.rulebook.ktlint.ConfusingPredicateRule.Companion.MSG_NEGATES
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ConfusingPredicateRuleTest {
    private val assertThatCode = assertThatRule { ConfusingPredicateRule() }

    @Test
    fun `Rule properties`() = ConfusingPredicateRule().assertProperties()

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

            fun baz() {
                1.takeIf { it is Number }
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

            fun baz() {
                1.takeUnless { it !is Number }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 8, Messages.get(MSG_NEGATES, "takeUnless")),
            LintViolation(6, 18, Messages.get(MSG_EQUALS, "filterNot")),
            LintViolation(10, 7, Messages.get(MSG_NEGATES, "takeIf")),
        )

    @Test
    fun `Skip chained conditions`() =
        assertThatCode(
            """
            fun foo() {
                "".takeIf { !it.isEmpty() && !it.isBlank() }
            }

            fun bar() {
                listOf(1, 2).filter { it != 0 && it > 0 }
            }

            fun baz() {
                1.takeIf { it !is Float && it !is Float }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
