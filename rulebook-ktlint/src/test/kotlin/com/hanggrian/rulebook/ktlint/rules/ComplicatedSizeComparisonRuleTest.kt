package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ComplicatedSizeComparisonRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { ComplicatedSizeComparisonRule() }

    @Test
    fun `Rule properties`() = ComplicatedSizeComparisonRule().assertProperties()

    @Test
    fun `Size check without operator`() =
        assertThatCode(
            """
            fun foo(val foo: List<Int>) {
                if (foo.isEmpty()) {
                } else if (foo.isNotEmpty()) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Size check with operator`() =
        assertThatCode(
            """
            fun foo(val foo: List<Int>) {
                if (foo.size == 0) {
                } else if (foo.size > 0) {
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, "Replace comparison with 'isEmpty'."),
            LintViolation(3, 20, "Replace comparison with 'isNotEmpty'."),
        )
}
