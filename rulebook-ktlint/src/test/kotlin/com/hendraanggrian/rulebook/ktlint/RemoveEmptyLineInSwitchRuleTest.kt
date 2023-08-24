package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RemoveEmptyLineInSwitchRuleTest {
    private val assertThatCode = assertThatRule { RemoveEmptyLineInSwitchRule() }

    @Test
    fun `Compact switch-case`() = assertThatCode(
        """
        fun execute(s: String) {
            when(s) {
                "Hello" -> {}
                "World" -> {}
                else -> {}
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Unnecessary empty lines`() = assertThatCode(
        """
        fun execute(s: String) {
            when(s) {

                "Hello" -> {}

                "World" -> {}

                else -> {}
            }
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 9, Messages[RemoveEmptyLineInSwitchRule.MSG]),
        LintViolation(6, 9, Messages[RemoveEmptyLineInSwitchRule.MSG]),
        LintViolation(8, 9, Messages[RemoveEmptyLineInSwitchRule.MSG])
    )

    @Test
    fun `However, last entry is ignored`() = assertThatCode(
        """
        fun execute(s: String) {
            when(s) {
                "Hello" -> {}
                "World" -> {}
                else -> {}

            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
