package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class LowercaseHexRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { LowercaseHexRule() }

    @Test
    fun `Rule properties`() = LowercaseHexRule().assertProperties()

    @Test
    fun `Lowercase hexadecimal letters`() =
        assertThatCode(
            """
            val foo = 0x00bb00

            fun bar() {
                println(0xaa00cc)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Uppercase hexadecimal letters`() =
        assertThatCode(
            """
            val foo = 0X00BB00

            fun bar() {
                println(0XAA00CC)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 11, "Use hexadecimal '0x00bb00'."),
            LintViolation(4, 13, "Use hexadecimal '0xaa00cc'."),
        )
}
