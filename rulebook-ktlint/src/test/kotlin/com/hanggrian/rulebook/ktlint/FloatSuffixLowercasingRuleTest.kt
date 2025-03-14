package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.FloatSuffixLowercasingRule.Companion.MSG_NUM
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class FloatSuffixLowercasingRuleTest {
    private val assertThatCode = assertThatRule { FloatSuffixLowercasingRule() }

    @Test
    fun `Rule properties`() = FloatSuffixLowercasingRule().assertProperties()

    @Test
    fun `Lowercase literal floats`() =
        assertThatCode(
            """
            val foo = 0f

            fun bar() {
                println(123f)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Uppercase literal floats`() =
        assertThatCode(
            """
            val foo = 0F

            fun bar() {
                println(123F)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, Messages[MSG_NUM]),
            LintViolation(4, 17, Messages[MSG_NUM]),
        )
}
