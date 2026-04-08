package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ComplicatedAssignmentRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ComplicatedAssignmentRule() }

    @Test
    fun `Rule properties`() = ComplicatedAssignmentRule().assertProperties()

    @Test
    fun `Shorthand assignments`() =
        assertThatCode(
            """
            fun foo() {
                var bar = 0
                bar += 1
                bar -= 1
                bar *= 1
                bar /= 1
                bar %= 1
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Complicated assignments`() =
        assertThatCode(
            """
            fun foo() {
                var bar = 0
                bar = bar + 1
                bar = bar - 1
                bar = bar * 1
                bar = bar / 1
                bar = bar % 1
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 9, "Use assignment operator '+='."),
            LintViolation(4, 9, "Use assignment operator '-='."),
            LintViolation(5, 9, "Use assignment operator '*='."),
            LintViolation(6, 9, "Use assignment operator '/='."),
            LintViolation(7, 9, "Use assignment operator '%='."),
        )

    @Test
    fun `Target leftmost operator`() =
        assertThatCode(
            """
            fun foo() {
                var bar = 0
                bar = bar + 1 - 2 * 3 / 4 % 5
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(
            3,
            9,
            "Use assignment operator '+='.",
        )
}
