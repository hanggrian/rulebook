package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ConfusingPredicateRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ConfusingPredicateRule() }

    @Test
    fun `Rule properties`() = ConfusingPredicateRule().assertProperties()

    @Test
    fun `Positive predicate calls`() =
        assertThatCode(
            """
            fun foo() {
                ''.takeUnless { it.isEmpty() }
            }

            fun bar() {
                listOf(1, 2).filterNot { it == 0 }
            }

            fun baz() {
                1.takeIf { it is Number }
            }

            fun qux() {
                setOf(1, 2).takeIf { it.isEmpty() }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Negative predicate calls`() =
        assertThatCode(
            """
            fun foo() {
                ''.takeIf { !it.isEmpty() }
            }

            fun bar() {
                listOf(1, 2).filter { it != 0 }
            }

            fun baz() {
                1.takeUnless { it !is Number }
            }

            fun qux() {
                setOf(1, 2).takeUnless { it.isNotEmpty() }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 8, "Omit negation and replace call with 'takeUnless'."),
            LintViolation(6, 18, "Use equals and replace call with 'filterNot'."),
            LintViolation(10, 7, "Omit negation and replace call with 'takeIf'."),
            LintViolation(14, 17, "Use 'isEmpty' and replace call with 'takeIf'."),
        )

    @Test
    fun `Skip chained conditions`() =
        assertThatCode(
            """
            fun foo() {
                ''.takeIf { !it.isEmpty() && !it.isBlank() }
            }

            fun bar() {
                listOf(1, 2).filter { it != 0 && it > 0 }
            }

            fun baz() {
                1.takeIf { it !is Float && it !is Float }
            }

            fun qux() {
                setOf(1, 2).takeUnless { it.isNotEmpty() || it.size == 1 }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
