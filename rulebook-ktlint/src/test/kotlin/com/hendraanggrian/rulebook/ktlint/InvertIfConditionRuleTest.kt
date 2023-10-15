package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.InvertIfConditionRule.Companion.MSG
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class InvertIfConditionRuleTest {
    private val assertThatCode = assertThatRule { InvertIfConditionRule() }

    @Test
    fun `Correct format`() = assertThatCode(
        """
        fun foo() {
            if (true) {
                return
            }
            println()
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Only 1 line in if statement`() = assertThatCode(
        """
        fun foo() {
            if (true) {
                println()
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `At least 2 lines in if statement`() = assertThatCode(
        """
        fun foo() {
            if (true) {
                println()
                println()
            }
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `If statement with else`() = assertThatCode(
        """
        fun foo() {
            if (true) {
                println()
                println()
            } else if (false) {
                println()
                println()
            } else {
                println()
                println()
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
