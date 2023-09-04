package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class InvertIfConditionRuleTest {
    private val assertThatCode = assertThatRule { InvertIfConditionRule() }

    @Test
    fun `Correct format`() = assertThatCode(
        """
        fun main() {
            if (false) {
                return
            }
            val one = 1
            val two = 2
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Only 1 line in if statement`() = assertThatCode(
        """
        fun main() {
            if (false) {
                val one = 1
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Empty block`() = assertThatCode(
        """
        fun oneLiner() {}
        fun oneLinerWithSpace() { }
        fun multiLiner() {
        }
        fun multiLinerWithSpace() {

        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `At least 2 lines in if statement`() = assertThatCode(
        """
        fun main() {
            if (true) {
                val one = 1
                val two = 2
            }
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[InvertIfConditionRule.MSG])

    @Test
    fun `If statement with else`() = assertThatCode(
        """
        fun main() {
            if (true) {
                val one = 1
                val two = 2
            } else if (false) {
                val three = 3
                val four = 4
            } else {
                val five = 5
                val six = 6
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
