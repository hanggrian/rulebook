package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class NumberSuffixForFloatRuleTest {
    private val assertThatCode = assertThatRule { NumberSuffixForFloatRule() }

    @Test
    fun `Rule properties`() = NumberSuffixForFloatRule().assertProperties()

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
            LintViolation(1, 13, "Tag float literal by 'f'."),
            LintViolation(4, 17, "Tag float literal by 'f'."),
        )
}
