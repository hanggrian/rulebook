package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.FunctionExpressionRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FunctionExpressionRuleTest {
    private val assertThatCode = assertThatRule { FunctionExpressionRule() }

    @Test
    fun `Expression function`() =
        assertThatCode(
            """
            fun sum(a: Int, b: Int): Int = a + b
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Regular function`() =
        assertThatCode(
            """
            fun sum(a: Int, b: Int): Int {
                return a + b
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 30, Messages[MSG])

    @Test
    fun `Regular function with whitespaces`() =
        assertThatCode(
            """
            fun sum(a: Int, b: Int): Int {

                return a + b
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 30, Messages[MSG])

    @Test
    fun `Regular function with comment`() =
        assertThatCode(
            """
            fun sum(a: Int, b: Int): Int {
                // some comment
                return a + b
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
