package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.FloatLiteralTaggingRule.Companion.MSG_HEX
import com.hanggrian.rulebook.ktlint.FloatLiteralTaggingRule.Companion.MSG_NUM
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class FloatLiteralTaggingRuleTest {
    private val assertThatCode = assertThatRule { FloatLiteralTaggingRule() }

    @Test
    fun `Rule properties`() = FloatLiteralTaggingRule().assertProperties()

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

    @Test
    fun `Lowercase literal hexadecimals`() =
        assertThatCode(
            """
            val foo = 0x00f

            fun bar() {
                println(0x123f)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 16, Messages[MSG_HEX]),
            LintViolation(4, 19, Messages[MSG_HEX]),
        )

    @Test
    fun `Uppercase literal hexadecimals`() =
        assertThatCode(
            """
            val foo = 0x00F

            fun bar() {
                println(0x123F)
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
