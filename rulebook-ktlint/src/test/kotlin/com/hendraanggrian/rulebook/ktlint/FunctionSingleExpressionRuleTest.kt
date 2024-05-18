package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.FunctionSingleExpressionRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FunctionSingleExpressionRuleTest {
    private val assertThatCode = assertThatRule { FunctionSingleExpressionRule() }

    @Test
    fun `Expression function`() =
        assertThatCode(
            """
            fun foo(): Int = 1
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Regular function`() =
        assertThatCode(
            """
            fun foo(): Int {
                return 1
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 16, Messages[MSG])

    @Test
    fun `Check property accessor`() =
        assertThatCode(
            """
            val bar: Int
                get() {
                    return 1
                }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 11, Messages[MSG])
}
