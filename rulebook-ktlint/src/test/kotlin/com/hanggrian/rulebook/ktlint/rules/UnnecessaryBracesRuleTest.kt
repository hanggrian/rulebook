package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnnecessaryBracesRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { UnnecessaryBracesRule() }

    @Test
    fun `Rule properties`() = UnnecessaryBracesRule().assertProperties()

    @Test
    fun `If not in else block`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    println()
                } else if (false) {
                    println()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `If in else block`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    println()
                } else {
                    if (false) {
                        println()
                    }
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 12, "Replace 'else' with 'else if' condition.")
}
